package com.github.kaklakariada.mediathek.download;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class HostAwareDownloadExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(HostAwareDownloadExecutor.class);

    private final Map<String, ExecutorService> executors = new HashMap<>();

    public void execute(DownloadingTask downloadingTask) {
        getExecutor(downloadingTask.getUrl()).execute(downloadingTask);
    }

    private ExecutorService getExecutor(ParsedUrl url) {
        ExecutorService executor = executors.get(url.getHost());
        if (executor == null) {
            executor = createExecutor(url.getHost());
            executors.put(url.getHost(), executor);
        }
        return executor;
    }

    private ExecutorService createExecutor(String host) {
        LOG.debug("Create new download executor for host {}", host);
        return Executors.newSingleThreadExecutor(new NamingThreadFactory("download-" + host + "-{0}"));
    }

    public void shutdown() {
        LOG.debug("Shutting down {} executors", executors.size());
        executors.values().forEach(ExecutorService::shutdown);
    }

    public void awaitTermination(int timeout, TimeUnit unit) {
        LOG.debug("Await termination of {} executors", executors.size());
        executors.values().forEach(e -> {
            try {
                e.awaitTermination(timeout, unit);
            } catch (final InterruptedException e1) {
                LOG.warn("Exception shutting down executors", e);
            }
        });
    }
}
