package com.github.kaklakariada.mediathek.programs;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.HtmlDocumentProcessor;

public class DreiSatMainPageProcessor extends HtmlDocumentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatMainPageProcessor.class);

    public DreiSatMainPageProcessor(CrawlerContext context) {
        super(context);
    }

    @Override
    public void process(Document doc) {
        LOG.debug("Processing url {} with titel '{}'", doc.baseUri(), doc.title());
        final Elements categoryLinks = doc.select("a.SubItem");
        LOG.debug("Found {} category links", categoryLinks.size());
        categoryLinks.stream()
                .limit(context.getConfig().getIterationLimit())
                .map(elem -> elem.attr("href"))
                .map(url -> url + "&type=1")
                .forEach(url -> context.submit(url, new DreiSatListPageProcessor(context, 0)));
    }
}
