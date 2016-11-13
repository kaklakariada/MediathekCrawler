package com.github.kaklakariada.mediathek.converter;

import java.net.URL;

import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ConverterInput {
    private final String content;
    private final ParsedUrl url;

    public ConverterInput(String content, ParsedUrl url) {
        this.content = content;
        this.url = url;
    }

    public ConverterInput(String content, URL url) {
        this(content, ParsedUrl.parse(url));
    }

    public String getContent() {
        return content;
    }

    public ParsedUrl getUrl() {
        return url;
    }
}
