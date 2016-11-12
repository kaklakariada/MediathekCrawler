package com.github.kaklakariada.mediathek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerContext {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerContext.class);
    private final CrawlingExecutor executor;
    private final Config config;

    public CrawlerContext() {
        this.executor = new CrawlingExecutor();
        this.config = new Config();
    }

    public void submit(String url, DocumentProcessor<?> processor) {
        final TvChannel channel = processor.getChannel();
        if (channel == null) {
            throw new IllegalStateException(
                    "No channel defined for processor " + processor + " of type " + processor.getClass().getName());
        }
        this.submit(channel, url, processor);
    }

    public void submit(TvChannel channel, String url, DocumentProcessor<?> processor) {
        executor.submit(channel, url, processor);
    }

    public Config getConfig() {
        return config;
    }
}
