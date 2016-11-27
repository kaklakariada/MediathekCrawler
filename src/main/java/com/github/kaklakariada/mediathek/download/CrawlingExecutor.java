package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerException;
import com.github.kaklakariada.mediathek.model.FileSize;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class CrawlingExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingExecutor.class);

    private final HostAwareDownloadExecutor downloadingExecutor;
    private final CountingExecutorService processingExecutor;

    private final KeySetView<String, Boolean> vistedUrls;

    private final ActiveThreadCounter counter;

    public CrawlingExecutor() {
        counter = new ActiveThreadCounter();
        downloadingExecutor = new HostAwareDownloadExecutor(counter);
        processingExecutor = CountingExecutorService.createProcessingExecutor(counter);
        vistedUrls = ConcurrentHashMap.newKeySet();
    }

    public void executeDownload(String url, DocumentProcessor<?> processor) {
        if (!alreadyProcessed(url)) {
            execute(new DownloadingTask(ParsedUrl.parse(url), processingExecutor, processor));
        }
    }

    private boolean alreadyProcessed(String url) {
        final boolean alreadyProcessed = !vistedUrls.add(url);
        if (alreadyProcessed) {
            LOG.debug("URL {} was already processed: ignore it now.", url);
        }
        return alreadyProcessed;
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
        processingExecutor.awaitTermination(5, TimeUnit.SECONDS);
        LOG.debug("Shutdown complete.");
    }

    public void await() {
        LOG.debug("Waiting until threads are started...");
        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
            throw new CrawlerException("Error sleeping", e);
        }
        counter.await();
    }
}
