package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.TvChannel;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class CrawlingExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final HostAwareDownloadExecutor downloadingExecutor;
    private final ExecutorService processingExecutor;

    public CrawlingExecutor() {
        downloadingExecutor = new HostAwareDownloadExecutor();
        processingExecutor = Executors.newCachedThreadPool(new NamingThreadFactory("processor-{0}"));
    }

    public void submit(TvChannel channel, String url, DocumentProcessor<?> processor) {
        final ParsedUrl parsedUrl = ParsedUrl.parse(url);
        downloadingExecutor.execute(new DownloadingTask(parsedUrl, processingExecutor, processor));
    }

    public void shutdown() {
        LOG.debug("Shutting down...");
        downloadingExecutor.shutdown();
        processingExecutor.shutdown();
        downloadingExecutor.awaitTermination(5, TimeUnit.SECONDS);
        try {
            processingExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            LOG.warn("Exception shutting down executors", e);
        }
        LOG.debug("Shutdown complete.");
    }
}
