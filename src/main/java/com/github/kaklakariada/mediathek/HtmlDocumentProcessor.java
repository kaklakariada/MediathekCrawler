package com.github.kaklakariada.mediathek;

import org.jsoup.nodes.Document;

public abstract class HtmlDocumentProcessor extends DocumentProcessor<Document> {
    public HtmlDocumentProcessor(CrawlerContext context, TvChannel channel) {
        super(context, channel, ContentFormat.HTML, Document.class);
    }
}
