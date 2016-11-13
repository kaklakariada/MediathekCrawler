package com.github.kaklakariada.mediathek.converter;

public abstract class ResponseConverter<T> {
    public abstract T convert(ConverterInput input);
}
