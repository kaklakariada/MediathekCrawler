package com.github.kaklakariada.mediathek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.collector.TvProgramCollector;
import com.github.kaklakariada.mediathek.download.CrawlingExecutor;
import com.github.kaklakariada.mediathek.model.TvProgram;

public class CrawlerContext {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerContext.class);
    private final CrawlingExecutor executor;
    private final TvProgramCollector tvProgramCollector;
    private final Config config;

    public CrawlerContext() {
        this.executor = new CrawlingExecutor();
        tvProgramCollector = new TvProgramCollector();
        this.config = new Config();
    }

    public CrawlingExecutor getExecutor() {
        return executor;
    }

    public void add(TvProgram program) {
        tvProgramCollector.add(program);
    }

    public Config getConfig() {
        return config;
    }
}
