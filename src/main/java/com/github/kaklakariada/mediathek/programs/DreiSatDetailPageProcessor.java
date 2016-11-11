package com.github.kaklakariada.mediathek.programs;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.HtmlDocumentProcessor;

public class DreiSatDetailPageProcessor extends HtmlDocumentProcessor {

    public DreiSatDetailPageProcessor(CrawlerContext context) {
        super(context);
    }

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatDetailPageProcessor.class);

    @Override
    public void process(Document doc) {
        LOG.debug("Processing detail page {}", doc.baseUri());
    }
}
