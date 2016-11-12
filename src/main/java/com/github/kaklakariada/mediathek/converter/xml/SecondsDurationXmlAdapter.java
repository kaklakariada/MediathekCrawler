package com.github.kaklakariada.mediathek.converter.xml;

import java.time.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SecondsDurationXmlAdapter extends XmlAdapter<Long, Duration> {

    @Override
    public Duration unmarshal(Long v) throws Exception {
        return Duration.ofSeconds(v);
    }

    @Override
    public Long marshal(Duration v) throws Exception {
        return v.getSeconds();
    }
}
