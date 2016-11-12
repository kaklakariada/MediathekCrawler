package com.github.kaklakariada.mediathek;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class CrawlingExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final ExecutorService downloadingExecutor;
    private final ExecutorService processingExecutor;

    public CrawlingExecutor() {
        downloadingExecutor = Executors.newSingleThreadExecutor();
        processingExecutor = Executors.newSingleThreadExecutor();
    }

    public void submit(TvChannel channel, String url, DocumentProcessor<?> processor) {
        final ParsedUrl parsedUrl = ParsedUrl.parse(url);
        downloadingExecutor.execute(new DownloadingTask(parsedUrl, processingExecutor, processor));
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
