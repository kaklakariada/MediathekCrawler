package com.github.kaklakariada.mediathek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.collector.TvProgramCollector;
import com.github.kaklakariada.mediathek.download.CrawlingExecutor;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.model.TvProgram;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;

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

    public void submit(String url, DocumentProcessor<?> processor) {
        final TvChannel channel = processor.getChannel();
        if (channel == null) {
            throw new IllegalStateException(
                    "No channel defined for processor " + processor + " of type " + processor.getClass().getName());
        }
        this.submit(channel, url, processor);
    }

    public void add(TvProgram program) {
        tvProgramCollector.add(program);
    }

    public void submit(TvChannel channel, String url, DocumentProcessor<?> processor) {
        executor.submit(channel, url, processor);
    }

    public Config getConfig() {
        return config;
    }
}
