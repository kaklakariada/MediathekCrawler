package com.github.kaklakariada.mediathek.converter;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaklakariada.mediathek.CrawlerException;

class JsonConverter<T> implements Converter<T> {

    private final Class<T> type;
    private final ObjectMapper objectMapper;

    public JsonConverter(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public T convert(ConverterInput input) {
        try {
            return objectMapper.readValue(input.getContent(), type);
        } catch (final IOException e) {
            throw new CrawlerException("Error parsing json response for url " + input.getUrl(), e);
        }
    }
}
