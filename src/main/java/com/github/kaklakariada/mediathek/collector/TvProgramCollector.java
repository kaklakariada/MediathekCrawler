package com.github.kaklakariada.mediathek.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.model.TvProgram;

public class TvProgramCollector {

    private static final Logger LOG = LoggerFactory.getLogger(TvProgramCollector.class);

    public void add(TvProgram program) {
        LOG.debug("Add tv program {}", program);
    }
}
