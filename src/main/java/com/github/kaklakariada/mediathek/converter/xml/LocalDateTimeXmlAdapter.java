package com.github.kaklakariada.mediathek.converter.xml;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return DATE_TIME_FORMATTER.format(v);
    }
}
