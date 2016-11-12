package com.github.kaklakariada.mediathek.model;

import java.util.EnumMap;
import java.util.Map;

public class DownloadUrls {
    private final Map<Resolution, DownloadUrl> urls = new EnumMap<>(Resolution.class);

    public static class DownloadUrl {
        private String url;
        private FileSize size;
    }
}
