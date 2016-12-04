package com.github.kaklakariada.mediathek.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class HtmlDocumentConverter implements Converter<Document> {
    @Override
    public Document convert(ConverterInput input) {
        return Jsoup.parse(input.getContent(), input.getUrl().toString());
    }
}
