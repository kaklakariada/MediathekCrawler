package com.github.kaklakariada.mediathek.converter;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.jsoup.Connection.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerException;

public class XmlConverter<T> extends ResponseConverter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(XmlConverter.class);

    private final Class<T> type;
    private final Unmarshaller unmarshaller;

    public XmlConverter(Class<T> type) {
        this.type = type;
        this.unmarshaller = createUnmarshaller(type);
    }

    private static Unmarshaller createUnmarshaller(Class<?> type) {
        try {

            final JAXBContext jc = JAXBContext.newInstance(type);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            // unmarshaller.setEventHandler(event -> false);
            return unmarshaller;
        } catch (final JAXBException e) {
            throw new CrawlerException("Error creating unmarshaller for " + type.getName(), e);
        }
    }

    @Override
    public T convert(Response response) {
        final String content = response.body();
        LOG.trace("Converting content of length {} to type {}:\n{}", content.length(), type.getName(), content);
        final Source source = new StreamSource(new StringReader(content), response.url().toString());
        try {
            final JAXBElement<T> element = unmarshaller.unmarshal(source, type);
            return element.getValue();
        } catch (final JAXBException e) {
            throw new CrawlerException("Error parsing xml response for url " + response.url(), e);
        }
    }
}
