package com.github.kaklakariada.mediathek.programs;

import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.MediathekCrawler;

public class DreiSatMainPageProcessor implements Consumer<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatMainPageProcessor.class);

    private final MediathekCrawler crawler;

    public DreiSatMainPageProcessor(MediathekCrawler crawler) {
        this.crawler = crawler;
    }

    @Override
    public void accept(Document doc) {
        LOG.debug("Processing url {} with titel '{}'", doc.baseUri(), doc.title());
        final Elements categoryLinks = doc.select("a.SubItem");
        LOG.debug("Found {} category links", categoryLinks.size());
        categoryLinks.stream().map(elem -> elem.attr("href"))
                .map(url -> url + "&type=1")
                .forEach(url -> crawler.enqueue(url, new DreiSatListPageProcessor(crawler, 0)));
    }

}
