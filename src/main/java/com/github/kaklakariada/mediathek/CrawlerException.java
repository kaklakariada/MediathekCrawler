package com.github.kaklakariada.mediathek;

public class CrawlerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlerException(String message) {
        super(message);
    }
}
