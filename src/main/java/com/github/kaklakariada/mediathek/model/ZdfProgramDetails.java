package com.github.kaklakariada.mediathek.model;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = ZdfProgramDetails.NAMESPACE, name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ZdfProgramDetails {

    static final String NAMESPACE = "http://www.zdf.de/ZDFmediathek/v2";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");

    @XmlElement(namespace = NAMESPACE, name = "video")
    private Video video;

    public Video getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "ZdfProgramDetails [video=" + video + "]";
    }

    public static class Video {

        @XmlElement(namespace = NAMESPACE, name = "information")
        private Information info;
        @XmlElement(namespace = NAMESPACE, name = "details")
        private Details details;

        public Information getInfo() {
            return info;
        }

        public Details getDetails() {
            return details;
        }

        @Override
        public String toString() {
            return "Video [info=" + info + ", details=" + details + "]";
        }
    }

    public static class Information {
        @XmlElement(namespace = NAMESPACE, name = "title")
        private String title;
        @XmlElement(namespace = NAMESPACE, name = "shortTitle")
        private String shortTitle;
        @XmlElement(namespace = NAMESPACE, name = "detail")
        private String detail;

        public String getTitle() {
            return title;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public String getDetail() {
            return detail;
        }

        @Override
        public String toString() {
            return "Information [title=" + title + ", shortTitle=" + shortTitle + ", detail=" + detail + "]";
        }
    }

    public static class Details {
        @XmlElement(namespace = NAMESPACE, name = "assetId")
        private String assetId;
        @XmlElement(namespace = NAMESPACE, name = "originChannelTitle")
        private String channelTitle;
        @XmlElement(namespace = NAMESPACE, name = "channel")
        private String channel;
        @XmlElement(namespace = NAMESPACE, name = "lengthSec")
        private int lengthSec;
        @XmlElement(namespace = NAMESPACE, name = "geolocation")
        private String geolocation;
        @XmlElement(namespace = NAMESPACE, name = "basename")
        private String basename;
        @XmlElement(namespace = NAMESPACE, name = "airtime")
        private String airtime;
        @XmlElement(namespace = NAMESPACE, name = "timetolive")
        private String timetolive;
        @XmlElement(namespace = NAMESPACE, name = "onlineairtime")
        private String onlineairtime;
        @XmlElement(namespace = NAMESPACE, name = "keywords")
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

        public int getLengthSec() {
            return lengthSec;
        }

        public String getGeolocation() {
            return geolocation;
        }

        public String getBasename() {
            return basename;
        }

        public String getAirtime() {
            return airtime;
        }

        public String getTimetolive() {
            return timetolive;
        }

        public String getOnlineairtime() {
            return onlineairtime;
        }

        public String getKeywords() {
            return keywords;
        }

        @Override
        public String toString() {
            return "Details [assetId=" + assetId + ", channelTitle=" + channelTitle + ", channel=" + channel
                    + ", lengthSec=" + lengthSec + ", geolocation=" + geolocation + ", basename=" + basename
                    + ", airtime=" + airtime + ", timetolive=" + timetolive + ", onlineairtime=" + onlineairtime
                    + ", keywords=" + keywords + "]";
        }
    }
}
