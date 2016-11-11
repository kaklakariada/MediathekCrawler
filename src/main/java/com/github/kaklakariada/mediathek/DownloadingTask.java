package com.github.kaklakariada.mediathek;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.converter.HtmlDocumentConverter;
import com.github.kaklakariada.mediathek.converter.ResponseConverter;
import com.github.kaklakariada.mediathek.converter.XmlConverter;
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

    public Response downloadUrl() {
        LOG.trace("Downloading url {}...", url);
        final Instant start = Instant.now();
        try {
            final Connection connection = Jsoup.connect(url.toString());
            final Response response = connection.execute();
            LOG.debug("Downloaded {} in {}", url, Duration.between(start, Instant.now()));
            return response;
        } catch (final IOException e) {
            throw new CrawlerException("Error downloading url " + url, e);
        }
    }

    @Override
    public void run() {
        final Response response = downloadUrl();
        processingExecutor.execute(() -> process(response, processor));
        delay();
    }

    private <T> void process(final Response response, DocumentProcessor<T> proc) {
        final ResponseConverter<T> converter = createConverter(proc);
        final T doc = converter.convert(response);
        proc.process(url, doc);
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseConverter<T> createConverter(DocumentProcessor<T> proc) {
        switch (proc.getContentFormat()) {
        case HTML:
            return (ResponseConverter<T>) new HtmlDocumentConverter();
        case XML:
            return new XmlConverter<>(proc.getInputType());
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