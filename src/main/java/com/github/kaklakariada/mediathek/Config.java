package com.github.kaklakariada.mediathek;

public class Config {

    private static final long ITERATION_LIMIT_MINIMUM = 1;

    private Config() {

    }

    public static Config load() {
        return new Config();
    }

    public long getIterationLimit() {
        return ITERATION_LIMIT_MINIMUM;
    }
}
