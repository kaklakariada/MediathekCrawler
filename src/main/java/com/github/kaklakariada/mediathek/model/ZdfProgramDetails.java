package com.github.kaklakariada.mediathek.model;

import static java.util.stream.Collectors.toSet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.zdf.de/ZDFmediathek/v2", name = "response")
public class ZdfProgramDetails {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");

    @XmlElement(name = "video", required = true)
    public Video video;

    public Video getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "ZdfProgramDetails [video=" + video + "]";
    }

    @XmlRootElement(name = "video")
    public static class Video {
        @XmlElement(name = "information")
        public Information info;

        public Information getInfo() {
            return info;
        }

        @Override
        public String toString() {
            return "Video [info=" + info + "]";
        }
    }

    public static class Information {
        @XmlElement(name = "title")
        public String title;
        @XmlElement(name = "shortTitle")
        public String shortTitle;
        @XmlElement(name = "detail")
        public String detail;

        @Override
        public String toString() {
            return "Information [title=" + title + ", shortTitle=" + shortTitle + ", detail=" + detail + "]";
        }
    }

    public static class Details {
        @XmlElement(name = "assetId")
        private String assetId;
        @XmlElement(name = "originChannelTitle")
        private String channelTitle;
        @XmlElement(name = "channel")
        private String channel;
        @XmlElement(name = "lengthSec")
        private int lengthSec;
        @XmlElement(name = "geolocation")
        private String geolocation;
        @XmlElement(name = "basename")
        private String basename;
        @XmlElement(name = "airtime")
        private String airtime;
        @XmlElement(name = "timetolive")
        private String timetolive;
        @XmlElement(name = "onlineairtime")
        private String onlineairtime;
        @XmlElement(name = "keywords")
        private String keywords;

        public String getAssetId() {
            return assetId;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public String getChannel() {
            return channel;
        }

        public Duration getDuration() {
            return Duration.ofSeconds(lengthSec);
        }

        public String getGeolocation() {
            return geolocation;
        }

        public String getBasename() {
            return basename;
        }

        public ZonedDateTime getAirtime() {
            return parseDateTime(airtime);
        }

        private static ZonedDateTime parseDateTime(String text) {
            return LocalDateTime.parse(text, DATE_TIME_FORMATTER).atZone(TIME_ZONE);
        }

        public ZonedDateTime getTimeToLive() {
            return parseDateTime(timetolive);
        }

        public ZonedDateTime getOnlineAirTime() {
            return parseDateTime(onlineairtime);
        }

        public Set<String> getKeywords() {
            return Arrays.stream(keywords.split(","))
                    .filter(k -> !k.isEmpty())
                    .map(String::trim)
                    .collect(toSet());
        }

        @Override
        public String toString() {
            return "Details [getAssetId()=" + getAssetId() + ", getChannelTitle()=" + getChannelTitle()
                    + ", getChannel()=" + getChannel() + ", getDuration()=" + getDuration() + ", getGeolocation()="
                    + getGeolocation() + ", getBasename()=" + getBasename() + ", getAirtime()=" + getAirtime()
                    + ", getTimeToLive()=" + getTimeToLive() + ", getOnlineAirTime()=" + getOnlineAirTime()
                    + ", getKeywords()=" + getKeywords() + "]";
        }
    }
}
