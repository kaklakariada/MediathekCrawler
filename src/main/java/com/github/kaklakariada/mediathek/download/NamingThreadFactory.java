package com.github.kaklakariada.mediathek.download;

import java.text.MessageFormat;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class NamingThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String nameTemplate;
    private final ThreadGroup group;

    public NamingThreadFactory(String nameTemplate) {
        final SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.nameTemplate = nameTemplate;
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread t = new Thread(group, r, getNextThreadName(), 0);

        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    private String getNextThreadName() {
        return MessageFormat.format(nameTemplate, threadNumber.getAndIncrement());
    }
}
