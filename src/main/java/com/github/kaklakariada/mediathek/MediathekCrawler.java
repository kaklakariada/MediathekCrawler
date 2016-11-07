package com.github.kaklakariada.mediathek;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class MediathekCrawler extends WebCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(MediathekCrawler.class);

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        final String href = url.getURL().toLowerCase();
        return href.startsWith("http://www.3sat.de/mediathek/?red=") && href.endsWith("&type=1");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        final String url = page.getWebURL().getURL();
        LOG.debug("URL: {}", url);
        if (url.equals("http://www.3sat.de/mediathek/")) {
            final HtmlParseData content = (HtmlParseData) page.getParseData();
            for (final WebURL outgoingUrl : content.getOutgoingUrls()) {
                if (outgoingUrl.getURL().startsWith("http://www.3sat.de/mediathek/?red=")) {
                    final String newUrl = outgoingUrl.getURL() + "&type=1";
                    LOG.debug("Add seed {}", newUrl);
                    getMyController().addSeed(newUrl);
                }
            }
        }

        if (page.getParseData() instanceof HtmlParseData) {
            final HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            final String text = htmlParseData.getText();
            final String html = htmlParseData.getHtml();
            final Set<WebURL> links = htmlParseData.getOutgoingUrls();

        }
    }

    public static void main(String[] args) throws Exception {
        final String crawlStorageFolder = "/tmp/crawl/root";
        final int numberOfCrawlers = 7;

        final CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        final PageFetcher pageFetcher = new PageFetcher(config);
        final RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        final RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        final CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.3sat.de/mediathek/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MediathekCrawler.class, numberOfCrawlers);
    }
}
