package com.github.kaklakariada.mediathek.converter;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.github.kaklakariada.mediathek.CrawlerException;

public class HtmlDocumentConverter extends ResponseConverter<Document> {

    @Override
    public Document convert(Response response) {
        try {
            return response.parse();
        } catch (final IOException e) {
            throw new CrawlerException("Error parsing response for url " + response.url(), e);
        }
    }
}
