package com.github.kaklakariada.mediathek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerContext {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerContext.class);
    private final CrawlingExecutor executor;

    public CrawlerContext() {
        this.executor = new CrawlingExecutor();
    }

    public void submit(String url, DocumentProcessor processor) {
        executor.submit(url, processor);
    }
}
