package com.github.kaklakariada.mediathek.programs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.kaklakariada.mediathek.converter.xml.LocalDateTimeXmlAdapter;
import com.github.kaklakariada.mediathek.converter.xml.SecondsDurationXmlAdapter;
import com.github.kaklakariada.mediathek.converter.xml.StringListXmlAdapter;

@XmlRootElement(namespace = ZdfProgramDetails.NAMESPACE, name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ZdfProgramDetails {

    static final String NAMESPACE = "http://www.zdf.de/ZDFmediathek/v2";

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
        @XmlJavaTypeAdapter(SecondsDurationXmlAdapter.class)
        private Duration duration;

        @XmlElement(namespace = NAMESPACE, name = "currentPage")
        private String currentPage;

        @XmlElement(namespace = NAMESPACE, name = "geolocation")
        private String geolocation;

        @XmlElement(namespace = NAMESPACE, name = "basename")
        private String basename;

        @XmlElement(namespace = NAMESPACE, name = "airtime")
        @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
        private LocalDateTime airtime;

        @XmlElement(namespace = NAMESPACE, name = "timetolive")
        @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
        private LocalDateTime timetolive;

        @XmlElement(namespace = NAMESPACE, name = "onlineairtime")
        @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
        private LocalDateTime onlineairtime;

        @XmlElement(namespace = NAMESPACE, name = "keywords")
        @XmlJavaTypeAdapter(StringListXmlAdapter.class)
        private List<String> keywords;

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
            return duration;
        }

        public String getCurrentPage() {
            return currentPage;
        }

        public String getGeolocation() {
            return geolocation;
        }

        public String getBasename() {
            return basename;
        }

        public LocalDateTime getAirtime() {
            return airtime;
        }

        public LocalDateTime getTimetolive() {
            return timetolive;
        }

        public LocalDateTime getOnlineairtime() {
            return onlineairtime;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        @Override
        public String toString() {
            return "Details [assetId=" + assetId + ", channelTitle=" + channelTitle + ", channel=" + channel
                    + ", duration=" + duration + ", currentPage=" + currentPage + ", geolocation=" + geolocation
                    + ", basename=" + basename + ", airtime=" + airtime + ", timetolive=" + timetolive
                    + ", onlineairtime=" + onlineairtime + ", keywords=" + keywords + "]";
        }
    }
}
