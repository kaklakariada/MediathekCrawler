package com.github.kaklakariada.mediathek.download;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.util.ParsedUrl;

class HostAwareDownloadExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(HostAwareDownloadExecutor.class);

    private final Map<String, CountingExecutorService> executors = new HashMap<>();

    private final ActiveThreadCounter counter;

    public HostAwareDownloadExecutor(ActiveThreadCounter counter) {
        this.counter = counter;
    }

    void execute(UrlTask downloadingTask) {
        getExecutor(downloadingTask.getUrl()).execute(downloadingTask);
    }

    private CountingExecutorService getExecutor(ParsedUrl url) {
        CountingExecutorService executor = executors.get(url.getHost());
        if (executor == null) {
            executor = CountingExecutorService.createDownloadExecutor(counter, url.getHost());
            executors.put(url.getHost(), executor);
        }
        return executor;
    }

    void shutdown() {
        LOG.debug("Shutting down {} executors", executors.size());
        executors.values().forEach(CountingExecutorService::shutdown);
    }

    void awaitTermination(int timeout, TimeUnit unit) {
        LOG.debug("Await termination of {} executors", executors.size());
        executors.values().forEach(e -> e.awaitTermination(timeout, unit));
    }
}
