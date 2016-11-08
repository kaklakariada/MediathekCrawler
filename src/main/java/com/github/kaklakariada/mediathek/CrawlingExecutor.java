package com.github.kaklakariada.mediathek;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlingExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final ExecutorService downloadingExecutor;
    private final ExecutorService processingExecutor;

    public CrawlingExecutor() {
        downloadingExecutor = Executors.newSingleThreadExecutor();
        processingExecutor = Executors.newSingleThreadExecutor();
    }

    public void submit(String url, Consumer<Document> processor) {
        downloadingExecutor.execute(new DownloadingTask(url, processingExecutor, processor));
    }

    public void shutdown() {
        LOG.debug("Shutting down...");
        downloadingExecutor.shutdown();
        processingExecutor.shutdown();
        try {
            downloadingExecutor.awaitTermination(5, TimeUnit.SECONDS);
            processingExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            LOG.warn("Exception shutting down executors", e);
        }
        LOG.debug("Shut down.");
    }
}