package com.github.kaklakariada.mediathek.programs;

import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;

public class DreiSatMainPageProcessor implements Consumer<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatMainPageProcessor.class);

    private final CrawlerContext context;

    public DreiSatMainPageProcessor(CrawlerContext context) {
        this.context = context;
    }

    @Override
    public void accept(Document doc) {
        LOG.debug("Processing url {} with titel '{}'", doc.baseUri(), doc.title());
        final Elements categoryLinks = doc.select("a.SubItem");
        LOG.debug("Found {} category links", categoryLinks.size());
        categoryLinks.stream().map(elem -> elem.attr("href"))
                .map(url -> url + "&type=1")
                .forEach(url -> context.enqueue(url, new DreiSatListPageProcessor(context, 0)));
    }

}
