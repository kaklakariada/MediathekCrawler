package com.github.kaklakariada.mediathek.collector;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.model.TvProgram;

public class TvProgramCollector {

    private static final Logger LOG = LoggerFactory.getLogger(TvProgramCollector.class);
    private final List<TvProgram> programs = new ArrayList<>();

    public void add(TvProgram program) {
        programs.add(program);
        LOG.debug("Add tv program #{}: {}", programs.size(), program);
    }
}
