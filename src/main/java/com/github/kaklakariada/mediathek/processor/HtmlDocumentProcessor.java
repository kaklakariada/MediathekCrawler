package com.github.kaklakariada.mediathek.processor;

import org.jsoup.nodes.Document;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.converter.ContentFormat;
import com.github.kaklakariada.mediathek.model.TvChannel;

public abstract class HtmlDocumentProcessor extends DocumentProcessor<Document> {
    public HtmlDocumentProcessor(CrawlerContext context, TvChannel channel) {
        super(context, channel, ContentFormat.HTML, Document.class);
    }
}
