package com.github.kaklakariada.mediathek.programs;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.processor.HtmlDocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class DreiSatMainPageProcessor extends HtmlDocumentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatMainPageProcessor.class);

    public DreiSatMainPageProcessor(CrawlerContext context) {
        super(context, TvChannel.DREI_SAT);
    }

    @Override
    public void process(ParsedUrl parsedUrl, Document doc) {
        LOG.debug("Processing url {} with titel '{}'", parsedUrl, doc.title());
        final Elements categoryLinks = doc.select("a.SubItem");
        LOG.debug("Found {} category links", categoryLinks.size());
        categoryLinks.stream()
                .limit(context.getConfig().getIterationLimit())
                .map(elem -> elem.attr("href"))
                .map(url -> url + "&type=1")
                .forEach(url -> context.getExecutor().executeDownload(url, new DreiSatListPageProcessor(context, 0)));
    }
}
