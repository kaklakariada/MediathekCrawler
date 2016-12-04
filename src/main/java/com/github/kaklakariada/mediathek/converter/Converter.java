package com.github.kaklakariada.mediathek.converter;

public interface Converter<T> {
    T convert(ConverterInput input);
}
