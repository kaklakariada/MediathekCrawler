package com.github.kaklakariada.mediathek;

import com.github.kaklakariada.mediathek.util.ParsedUrl;

public abstract class DocumentProcessor<T> {

    protected CrawlerContext context;
    private final Class<T> inputType;
    private final ContentFormat contentFormat;
    private final TvChannel channel;

    public DocumentProcessor(CrawlerContext context, TvChannel channel, ContentFormat contentFormat,
            Class<T> inputType) {
        this.context = context;
        this.channel = channel;
        this.contentFormat = contentFormat;
        this.inputType = inputType;
    }

    public abstract void process(ParsedUrl url, T doc);

    public TvChannel getChannel() {
        return channel;
    }

    public Class<T> getInputType() {
        return inputType;
    }

    public ContentFormat getContentFormat() {
        return contentFormat;
    }
}
