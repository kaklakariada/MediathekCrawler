package com.github.kaklakariada.mediathek;

import com.github.kaklakariada.mediathek.programs.DreiSatMainPageProcessor;

public class Main {
    public static void main(String[] args) {
        final MediathekCrawler crawler = new MediathekCrawler();
        crawler.enqueue("http://www.3sat.de/mediathek/", new DreiSatMainPageProcessor(crawler));
    }
}
