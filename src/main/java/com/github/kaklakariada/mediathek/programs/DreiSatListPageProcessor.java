package com.github.kaklakariada.mediathek.programs;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.DocumentProcessor;

public class DreiSatListPageProcessor extends DocumentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DreiSatListPageProcessor.class);
    private final CrawlerContext context;
    private final int pageNumber;

    public DreiSatListPageProcessor(CrawlerContext context, int pageNumber) {
        this.context = context;
        this.pageNumber = pageNumber;
    }

    @Override
    public void process(Document doc) {
        final List<String> detailPageUrls = doc.select("a.MediathekLink").stream()
                .map(elem -> elem.attr("href"))
                .filter(url -> url.contains("mode=play"))
                .distinct()
                .collect(toList());
        LOG.debug("Processing list page #{} with url {} and {} detail links", pageNumber, doc.baseUri(),
                detailPageUrls.size());

        detailPageUrls
                .stream()
                .limit(context.getConfig().getIterationLimit())
                .forEach(url -> context.submit(url, new DreiSatDetailPageProcessor(context)));

        submitNextPageLink(doc.select("a.mediathek_menu_low"));
    }

    private void submitNextPageLink(Elements links) {
        if (pageNumber >= context.getConfig().getIterationLimit()) {
            LOG.debug("Skipping next page link check on page #{}", pageNumber);
            return;
        }
        final String nextPageNumber = String.valueOf(pageNumber + 2);
        final Optional<String> nextPageUrl = links.stream()
                .filter(elem -> nextPageNumber.equals(elem.text()))
                .map(elem -> elem.attr("href"))
                .findFirst();
        if (nextPageUrl.isPresent()) {
            LOG.debug("Link to next page {} found: {}", pageNumber + 1, nextPageUrl.get());
            context.submit(nextPageUrl.get(), new DreiSatListPageProcessor(context, pageNumber + 1));
        }
    }
}
