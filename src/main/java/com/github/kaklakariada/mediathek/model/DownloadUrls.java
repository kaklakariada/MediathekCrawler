package com.github.kaklakariada.mediathek.model;

import java.util.EnumMap;
import java.util.Map;

public class DownloadUrls {
    private final Map<Resolution, DownloadUrl> urls;

    private DownloadUrls(Builder builder) {
        this.urls = new EnumMap<>(builder.urls);
    }

    public Map<Resolution, DownloadUrl> getUrls() {
        return urls;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "DownloadUrls [urls=" + urls + "]";
    }

    public static class DownloadUrl {
        private final String url;
        private final FileSize size;

        private DownloadUrl(String url, FileSize size) {
            this.url = url;
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public FileSize getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "DownloadUrl [url=" + url + ", size=" + size + "]";
        }
    }

    public static class Builder {
        private final Map<Resolution, DownloadUrl> urls = new EnumMap<>(Resolution.class);

        private Builder() {
        }

        public Builder addUrl(Resolution resolution, String url, FileSize fileSize) {
            urls.put(resolution, new DownloadUrl(url, fileSize));
            return this;
        }

        public DownloadUrls build() {
            return new DownloadUrls(this);
        }
    }
}
