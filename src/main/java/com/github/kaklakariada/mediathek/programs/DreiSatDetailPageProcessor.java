package com.github.kaklakariada.mediathek.programs;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.DocumentProcessor;

public class DreiSatDetailPageProcessor extends DocumentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatDetailPageProcessor.class);

    public DreiSatDetailPageProcessor(CrawlerContext context) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void process(Document doc) {
        LOG.debug("Processing detail page {}", doc.baseUri());
    }

}
