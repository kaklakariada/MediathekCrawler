package com.github.kaklakariada.mediathek.util;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParsedUrl {

    private final URL url;
    private final Map<String, List<String>> queryParams;

    public ParsedUrl(URL url, Map<String, List<String>> queryArgs) {
        this.url = url;
        this.queryParams = queryArgs;
    }

    public String getParam(String paramName) {
        final List<String> values = getListParam(paramName);
        if (values == null) {
            throw new IllegalArgumentException("Param '" + paramName + "' not found in params " + queryParams);
        }
        if (values.isEmpty()) {
            return null;
        }
        if (values.size() == 1) {
            return values.get(0);
        }
        throw new IllegalArgumentException("Multiple values found for param '" + paramName + "': " + values);
    }

    private List<String> getListParam(String paramName) {
        return queryParams.get(paramName);
    }

    public String getHost() {
        return url.getHost();
    }

    public static ParsedUrl parse(String urlString) {
        final URL url = createUrl(urlString);
        final Map<String, List<String>> queryArgs = splitQuery(url.getQuery());
        return new ParsedUrl(url, queryArgs);
    }

    private static URL createUrl(String urlString) {
        try {
            return new URL(urlString);
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException("Error parsing url '" + urlString + "'", e);
        }
    }

    private static Map<String, List<String>> splitQuery(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }
        return Arrays.stream(query.split("&"))
                .map(ParsedUrl::splitQueryParameter)
                .collect(Collectors.groupingBy(SimpleImmutableEntry::getKey, LinkedHashMap::new,
                        mapping(Map.Entry::getValue, toList())));
    }

    private static SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new SimpleImmutableEntry<>(key, value);
    }
}
