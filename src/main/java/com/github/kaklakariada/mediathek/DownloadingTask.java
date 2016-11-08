package com.github.kaklakariada.mediathek;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadingTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadingTask.class);

    private final String url;
    private final ExecutorService processingExecutor;
    private final Consumer<Document> processor;

    public DownloadingTask(String url, ExecutorService processingExecutor, Consumer<Document> processor) {
        this.url = url;
        this.processingExecutor = processingExecutor;
        this.processor = processor;
    }

    public Document downloadUrl() {
        LOG.trace("Downloading url {}...", url);
        try {
            final Document doc = Jsoup.connect(url).get();
            LOG.trace("Download of {} finished.", url);
            return doc;
        } catch (final IOException e) {
            throw new CrawlerException("Error downloading url " + url, e);
        }
    }

    @Override
    public void run() {
        final Document doc = downloadUrl();
        processingExecutor.execute(() -> processor.accept(doc));
    }
}