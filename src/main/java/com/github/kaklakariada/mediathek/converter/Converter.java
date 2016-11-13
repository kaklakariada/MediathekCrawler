package com.github.kaklakariada.mediathek.converter;

public abstract class Converter<T> {
    public abstract T convert(ConverterInput input);
}
