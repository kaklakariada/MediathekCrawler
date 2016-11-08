package com.github.kaklakariada.mediathek.programs;

import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.MediathekCrawler;

public class DreiSatListPageProcessor implements Consumer<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatListPageProcessor.class);
    private final MediathekCrawler crawler;
    private final int pageNumber;

    public DreiSatListPageProcessor(MediathekCrawler crawler, int pageNumber) {
        this.crawler = crawler;
        this.pageNumber = pageNumber;
    }

    @Override
    public void accept(Document doc) {
        LOG.debug("Processing list page #{} with url {}", pageNumber, doc.baseUri());
    }
}
