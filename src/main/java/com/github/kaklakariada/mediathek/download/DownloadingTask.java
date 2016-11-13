package com.github.kaklakariada.mediathek.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerException;
import com.github.kaklakariada.mediathek.converter.ConverterInput;
import com.github.kaklakariada.mediathek.converter.HtmlDocumentConverter;
import com.github.kaklakariada.mediathek.converter.JsonConverter;
import com.github.kaklakariada.mediathek.converter.ResponseConverter;
import com.github.kaklakariada.mediathek.converter.XmlConverter;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class DownloadingTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadingTask.class);

    private static final int THROTTLING_DELAY_MILLIS = 200;

    private final ParsedUrl url;
    private final ExecutorService processingExecutor;
    private final DocumentProcessor<?> processor;

    public DownloadingTask(ParsedUrl url, ExecutorService processingExecutor, DocumentProcessor<?> processor) {
        this.url = url;
        this.processingExecutor = processingExecutor;
        this.processor = processor;
    }

    private ConverterInput downloadUrl() {
        LOG.trace("Downloading url {}...", url);
        final Instant start = Instant.now();
        final StringBuilder b = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.getContent(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                b.append(line).append('\n');
            }
        } catch (final IOException e) {
            throw new CrawlerException("Error reading from url " + url, e);
        }

        // final Connection connection = Jsoup.connect(url.toString());
        // final Response response = connection.execute();
        final ConverterInput converterInput = new ConverterInput(b.toString(), url);
        LOG.trace("Downloaded {} in {}", url, Duration.between(start, Instant.now()));
        return converterInput;
    }

    public ParsedUrl getUrl() {
        return url;
    }

    @Override
    public void run() {
        final ConverterInput response = downloadUrl();
        processingExecutor.execute(() -> process(response, processor));
        delay();
    }

    private <T> void process(final ConverterInput converterInput, DocumentProcessor<T> proc) {
        final ResponseConverter<T> converter = createConverter(proc);
        final T doc = converter.convert(converterInput);
        proc.process(url, doc);
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseConverter<T> createConverter(DocumentProcessor<T> proc) {
        switch (proc.getContentFormat()) {
        case HTML:
            return (ResponseConverter<T>) new HtmlDocumentConverter();
        case XML:
            return new XmlConverter<>(proc.getInputType());
        case JSON:
            return new JsonConverter<>(proc.getInputType());
        default:
            throw new CrawlerException("No converter defined for " + proc.getContentFormat());
        }
    }

    private void delay() {
        try {
            Thread.sleep(THROTTLING_DELAY_MILLIS);
        } catch (final InterruptedException e) {
            LOG.warn("Exception when sleeping", e);
        }
    }
}