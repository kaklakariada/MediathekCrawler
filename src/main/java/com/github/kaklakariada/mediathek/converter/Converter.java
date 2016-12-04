package com.github.kaklakariada.mediathek.converter;

@FunctionalInterface
public interface Converter<T> {
    T convert(ConverterInput input);
}
