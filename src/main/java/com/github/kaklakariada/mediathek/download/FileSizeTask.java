package com.github.kaklakariada.mediathek.download;

import java.net.HttpURLConnection;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerException;
import com.github.kaklakariada.mediathek.model.FileSize;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

class FileSizeTask implements UrlTask {
    private static final Logger LOG = LoggerFactory.getLogger(FileSizeTask.class);
    private static final int TIMEOUT_MILLIS = 3000;

    private final TvChannel channel;
    private final ParsedUrl url;
    private final Consumer<FileSize> callback;

    private final CountingExecutorService processingExecutor;

    FileSizeTask(TvChannel channel, ParsedUrl url, CountingExecutorService processingExecutor,
            Consumer<FileSize> callback) {
        this.channel = channel;
        this.url = url;
        this.processingExecutor = processingExecutor;
        this.callback = callback;
    }

    @Override
    public void run() {
        final HttpURLConnection conn = url.openConnection();
        try {
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            final long contentLength = conn.getContentLengthLong();
            if (contentLength < 0) {
                throw new CrawlerException("Got invalid content length " + contentLength + " for url " + url);
            }
            final FileSize fileSize = FileSize.ofBytes(contentLength);
            LOG.debug("Got file size {} for url {}", fileSize, url);
            processingExecutor.execute(() -> callback.accept(fileSize));
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public ParsedUrl getUrl() {
        return url;
    }

    @Override
    public TvChannel getChannel() {
        return channel;
    }
}
