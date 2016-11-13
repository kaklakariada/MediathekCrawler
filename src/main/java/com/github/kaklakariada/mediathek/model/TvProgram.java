package com.github.kaklakariada.mediathek.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class TvProgram {
    private final TvChannel channel;
    private final String title;
    private final String topic;
    private final String description;
    private final LocalDateTime airtime;
    private final Duration duration;
    private final String websiteUrl;
    private final String captionUrl;
    private final DownloadUrls downloadUrls;

    public TvProgram(Builder b) {
        this.channel = Objects.requireNonNull(b.channel, "channel");
        this.title = Objects.requireNonNull(b.title, "title");
        this.topic = b.topic;
        this.description = Objects.requireNonNull(b.description, "description");
        this.airtime = Objects.requireNonNull(b.airtime, "airtime");
        this.duration = Objects.requireNonNull(b.duration, "duration");
        this.websiteUrl = Objects.requireNonNull(b.websiteUrl, "websiteUrl");
        this.captionUrl = b.captionUrl;
        this.downloadUrls = b.downloadUrlsBuilder.build();
    }

    public TvChannel getChannel() {
        return channel;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAirtime() {
        return airtime;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getCaptionUrl() {
        return captionUrl;
    }

    public DownloadUrls getDownloadUrls() {
        return downloadUrls;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "TvProgram [channel=" + channel + ", title=" + title + ", topic=" + topic + ", description="
                + description + ", airtime=" + airtime + ", duration=" + duration + ", websiteUrl=" + websiteUrl
                + ", captionUrl=" + captionUrl + ", downloadUrls=" + downloadUrls + "]";
    }

    public static class Builder {
        private TvChannel channel;
        private String title;
        private String topic;
        private String description;
        private LocalDateTime airtime;
        private Duration duration;
        private String websiteUrl;
        private String captionUrl;
        private final com.github.kaklakariada.mediathek.model.DownloadUrls.Builder downloadUrlsBuilder = DownloadUrls
                .newBuilder();

        private Builder() {
        }

        public Builder channel(TvChannel channel) {
            this.channel = channel;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder airtime(LocalDateTime airtime) {
            this.airtime = airtime;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder websiteUrl(String websiteUrl) {
            this.websiteUrl = websiteUrl;
            return this;
        }

        public Builder captionUrl(String captionUrl) {
            this.captionUrl = captionUrl;
            return this;
        }

        public Builder addDownloadUrl(Resolution resolution, String url, FileSize fileSize) {
            this.downloadUrlsBuilder.addUrl(resolution, url, fileSize);
            return this;
        }

        public TvProgram build() {
            return new TvProgram(this);
        }
    }
}
