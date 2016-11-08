package com.github.kaklakariada.mediathek.programs;

import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;

public class DreiSatListPageProcessor implements Consumer<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatListPageProcessor.class);
    private final CrawlerContext context;
    private final int pageNumber;

    public DreiSatListPageProcessor(CrawlerContext context, int pageNumber) {
        this.context = context;
        this.pageNumber = pageNumber;
    }

    @Override
    public void accept(Document doc) {
        LOG.debug("Processing list page #{} with url {}", pageNumber, doc.baseUri());
    }
}
