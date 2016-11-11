package com.github.kaklakariada.mediathek.converter;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.jsoup.Connection.Response;

import com.github.kaklakariada.mediathek.CrawlerException;

public class XmlConverter<T> extends ResponseConverter<T> {

    private final Class<T> type;
    private final Unmarshaller unmarshaller;

    public XmlConverter(Class<T> type) {
        this.type = type;
        this.unmarshaller = createUnmarshaller(type);
    }

    private static Unmarshaller createUnmarshaller(Class<?> type) {
        try {
            final JAXBContext jc = JAXBContext.newInstance(type);
            return jc.createUnmarshaller();
        } catch (final JAXBException e) {
            throw new CrawlerException("Error creating unmarshaller for " + type.getName(), e);
        }
    }

    @Override
    public T convert(Response response) {
        final Source source = new StreamSource(new StringReader(response.body()));
        try {
            return unmarshaller.unmarshal(source, type).getValue();
        } catch (final JAXBException e) {
            throw new CrawlerException("Error parsing xml response for url " + response.url(), e);
        }
    }
}
