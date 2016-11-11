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
        executor.submit(url, processor);
    }

    public Config getConfig() {
        return config;
    }
}
