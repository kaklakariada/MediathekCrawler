package com.github.kaklakariada.mediathek.converter.xml;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringListXmlAdapter extends XmlAdapter<String, List<String>> {

    @Override
    public List<String> unmarshal(String v) throws Exception {
        return Arrays.stream(v.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(toList());
    }

    @Override
    public String marshal(List<String> v) throws Exception {
        return v.stream().collect(joining(","));
    }
}
