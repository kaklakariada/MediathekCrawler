package com.github.kaklakariada.mediathek.programs;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.processor.HtmlDocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class DreiSatListPageProcessor extends HtmlDocumentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatListPageProcessor.class);
    private final int pageNumber;

    public DreiSatListPageProcessor(CrawlerContext context, int pageNumber) {
        super(context, TvChannel.DREI_SAT);
        this.pageNumber = pageNumber;
    }

    @Override
    public void process(ParsedUrl parsedUrl, Document doc) {
        final List<String> objectIds = doc.select("a.MediathekLink").stream()
                .map(elem -> elem.attr("href"))
                .filter(url -> url.contains("mode=play"))
                .distinct()
                .map(ParsedUrl::parse)
                .map(url -> url.getParam("obj"))
                .collect(toList());

        LOG.debug("Processing list page #{} with url {} and {} detail pages", pageNumber, parsedUrl, objectIds.size());

        objectIds.stream()
                .limit(context.getConfig().getIterationLimit())
                .map(objId -> "http://www.3sat.de/mediathek/xmlservice/v2/web/beitragsDetails?ak=web&id=" + objId)
                .forEach(url -> context.getExecutor().executeDownload(url,
                        new ZdfProgramDetailsXmlProcessor(context, getChannel())));

        submitNextPageLink(doc);
    }

    private void submitNextPageLink(Document doc) {
        if (pageNumber >= context.getConfig().getIterationLimit()) {
            LOG.debug("Skipping next page link check on page #{}", pageNumber);
            return;
        }
        final String nextPageNumber = String.valueOf(pageNumber + 2);
        final Optional<String> nextPageUrl = doc.select("a.mediathek_menu_low").stream()
                .filter(elem -> nextPageNumber.equals(elem.text()))
                .map(elem -> elem.attr("href"))
                .findFirst();
        if (nextPageUrl.isPresent()) {
            LOG.debug("Link to next page {} found: {}", pageNumber + 1, nextPageUrl.get());
            context.getExecutor().executeDownload(nextPageUrl.get(),
                    new DreiSatListPageProcessor(context, pageNumber + 1));
        }
    }
}
