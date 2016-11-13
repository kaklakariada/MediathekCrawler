package com.github.kaklakariada.mediathek.converter;

import com.github.kaklakariada.mediathek.CrawlerException;

public class ConverterFactory {
    @SuppressWarnings("unchecked")
    public <T> Converter<T> createConverter(ContentFormat format, Class<T> type) {
        switch (format) {
        case HTML:
            return (Converter<T>) new HtmlDocumentConverter();
        case XML:
            return new XmlConverter<>(type);
        case JSON:
            return new JsonConverter<>(type);
        default:
            throw new CrawlerException("No converter defined for " + format);
        }
    }
}
