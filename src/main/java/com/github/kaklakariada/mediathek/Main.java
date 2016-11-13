package com.github.kaklakariada.mediathek;

import com.github.kaklakariada.mediathek.programs.DreiSatMainPageProcessor;

public class Main {
    public static void main(String[] args) {
        final CrawlerContext crawler = new CrawlerContext();
        crawler.getExecutor().executeDownload("http://www.3sat.de/mediathek/", new DreiSatMainPageProcessor(crawler));
    }
}
