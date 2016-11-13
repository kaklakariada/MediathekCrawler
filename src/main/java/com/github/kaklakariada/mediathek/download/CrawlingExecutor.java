package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.model.FileSize;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class CrawlingExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final HostAwareDownloadExecutor downloadingExecutor;
    private final ExecutorService processingExecutor;

    public CrawlingExecutor() {
        downloadingExecutor = new HostAwareDownloadExecutor();
        final int maxNumberOfThreads = Runtime.getRuntime().availableProcessors();
        LOG.info("Using processing thread pool with {} threads", maxNumberOfThreads);
        processingExecutor = Executors.newFixedThreadPool(maxNumberOfThreads, new NamingThreadFactory("processor-{0}"));
    }

    public void executeDownload(String url, DocumentProcessor<?> processor) {
        final ParsedUrl parsedUrl = ParsedUrl.parse(url);
        execute(new DownloadingTask(parsedUrl, processingExecutor, processor));
    }

    public void executeGetContentLength(TvChannel channel, String url, Consumer<FileSize> callback) {
        execute(new FileSizeTask(channel, ParsedUrl.parse(url), processingExecutor, callback));
    }

    private void execute(UrlTask downloadingTask) {
        downloadingExecutor.execute(downloadingTask);
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
