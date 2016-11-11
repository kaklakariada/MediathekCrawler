package com.github.kaklakariada.mediathek;

import org.jsoup.nodes.Document;

public abstract class HtmlDocumentProcessor extends DocumentProcessor<Document> {
    public HtmlDocumentProcessor(CrawlerContext context) {
        super(context, ContentFormat.HTML, Document.class);
    }
}
