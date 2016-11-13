package com.github.kaklakariada.mediathek.programs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.converter.ContentFormat;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.model.TvProgram.Builder;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsJsonProcessor extends DocumentProcessor<ZdfProgramDetailsJson> {

    private static final Logger LOG = LoggerFactory.getLogger(ZdfProgramDetailsJsonProcessor.class);
    private final Builder programBuilder;

    public ZdfProgramDetailsJsonProcessor(CrawlerContext context, TvChannel channel, Builder programBuilder) {
        super(context, channel, ContentFormat.JSON, ZdfProgramDetailsJson.class);
        this.programBuilder = programBuilder;
    }

    @Override
    public void process(ParsedUrl url, ZdfProgramDetailsJson doc) {
        LOG.debug("Processing url {}: {}", url, doc);
    }

}
