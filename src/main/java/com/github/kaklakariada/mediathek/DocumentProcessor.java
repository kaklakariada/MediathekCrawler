package com.github.kaklakariada.mediathek;

public abstract class DocumentProcessor<T> {

    protected CrawlerContext context;
    private final Class<T> inputType;
    private final ContentFormat contentFormat;

    public DocumentProcessor(CrawlerContext context, ContentFormat contentFormat, Class<T> inputType) {
        this.context = context;
        this.contentFormat = contentFormat;
        this.inputType = inputType;
    }

    public abstract void process(T doc);

    public Class<T> getInputType() {
        return inputType;
    }

    public ContentFormat getContentFormat() {
        return contentFormat;
    }
}
