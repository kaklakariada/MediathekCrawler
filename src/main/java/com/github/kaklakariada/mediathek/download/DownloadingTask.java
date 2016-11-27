package com.github.kaklakariada.mediathek.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerException;
import com.github.kaklakariada.mediathek.converter.Converter;
import com.github.kaklakariada.mediathek.converter.ConverterFactory;
import com.github.kaklakariada.mediathek.converter.ConverterInput;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

class DownloadingTask implements UrlTask {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadingTask.class);

    private static final int THROTTLING_DELAY_MILLIS = 200;

    private final ParsedUrl url;
    private final CountingExecutorService processingExecutor;
    private final DocumentProcessor<?> processor;

    private final ConverterFactory converterFactory;

    DownloadingTask(ParsedUrl url, CountingExecutorService processingExecutor, DocumentProcessor<?> processor) {
        this.url = url;
        this.processingExecutor = processingExecutor;
        this.processor = processor;
        this.converterFactory = new ConverterFactory();
    }

    private String downloadUrl() {
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

        final String content = b.toString();
        LOG.trace("Downloaded {} chars from {} in {}", content.length(), url, Duration.between(start, Instant.now()));
        return content;
    }

    @Override
    public ParsedUrl getUrl() {
        return url;
    }

    @Override
    public TvChannel getChannel() {
        return processor.getChannel();
    }

    @Override
    public void run() {
        final String content = downloadUrl();
        processingExecutor.execute(() -> process(content, processor));
        delay();
    }

    private <T> void process(final String content, DocumentProcessor<T> proc) {
        final Converter<T> converter = converterFactory.createConverter(proc.getContentFormat(), proc.getInputType());
        final T doc = converter.convert(new ConverterInput(content, url));
        proc.process(url, doc);
    }

    private void delay() {
        try {
            Thread.sleep(THROTTLING_DELAY_MILLIS);
        } catch (final InterruptedException e) {
            LOG.warn("Exception when sleeping", e);
        }
    }
}