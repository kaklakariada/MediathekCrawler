package com.github.kaklakariada.mediathek.converter;

import org.jsoup.Connection.Response;

public abstract class ResponseConverter<T> {
    public abstract T convert(Response response);
}
