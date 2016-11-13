package com.github.kaklakariada.mediathek.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlDocumentConverter extends ResponseConverter<Document> {
    @Override
    public Document convert(ConverterInput input) {
        return Jsoup.parse(input.getContent(), input.getUrl().toString());
    }
}
