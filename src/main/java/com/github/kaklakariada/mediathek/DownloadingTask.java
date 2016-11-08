package com.github.kaklakariada.mediathek;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadingTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadingTask.class);

    private static final int THROTTLING_DELAY_MILLIS = 200;

    private final String url;
    private final ExecutorService processingExecutor;
    private final Consumer<Document> processor;

    public DownloadingTask(String url, ExecutorService processingExecutor, Consumer<Document> processor) {
        this.url = url;
        this.processingExecutor = processingExecutor;
        this.processor = processor;
    }

    public Response downloadUrl() {
        LOG.trace("Downloading url {}...", url);
        final Instant start = Instant.now();
        try {
            final Connection connection = Jsoup.connect(url);
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
        processingExecutor.execute(() -> {
            final Document doc = parse(response);
            processor.accept(doc);
        });
        delay();
    }

    private Document parse(final Response response) {
        try {
            return response.parse();
        } catch (final IOException e) {
            throw new CrawlerException("Error parsing response for url " + url, e);
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