package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountingExecutorService {
    private static final Logger LOG = LoggerFactory.getLogger(CountingExecutorService.class);

    private final ExecutorService executor;
    private final ActiveThreadCounter counter;

    private CountingExecutorService(ExecutorService executor, ActiveThreadCounter counter) {
        this.executor = executor;
        this.counter = counter;
    }

    public static CountingExecutorService createProcessingExecutor(ActiveThreadCounter counter) {
        final int maxNumberOfThreads = Runtime.getRuntime().availableProcessors();
        LOG.info("Create processing thread pool with {} threads", maxNumberOfThreads);
        final ExecutorService executor = Executors.newFixedThreadPool(maxNumberOfThreads,
                new NamingThreadFactory("processor-{0}"));
        return new CountingExecutorService(executor, counter);
    }

    static CountingExecutorService createDownloadExecutor(ActiveThreadCounter counter, String host) {
        LOG.trace("Create download executor for host {}", host);
        final ExecutorService executor = Executors.newSingleThreadExecutor(new NamingThreadFactory("dl-" + host));
        return new CountingExecutorService(executor, counter);
    }

    public void execute(Runnable r) {
        counter.threadStarted();
        executor.execute(() -> {
            try {
                r.run();
            } finally {
                counter.threadFinished();
            }
        });
    }

    public void shutdown() {
        LOG.trace("Shutting down executor");
        executor.shutdown();
    }

    public void awaitTermination(long timeout, TimeUnit unit) {
        LOG.trace("Await termination");
        try {
            executor.awaitTermination(timeout, unit);
        } catch (final InterruptedException e) {
            LOG.warn("Exception shutting down executor " + executor, e);
            Thread.currentThread().interrupt();
        }
    }
}
