package com.github.kaklakariada.mediathek.programs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.ContentFormat;
import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.TvChannel;
import com.github.kaklakariada.mediathek.model.ZdfProgramDetails;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsProcessor extends DocumentProcessor<ZdfProgramDetails> {

    public ZdfProgramDetailsProcessor(CrawlerContext context, TvChannel channel) {
        super(context, channel, ContentFormat.XML, ZdfProgramDetails.class);
    }

    private static final Logger LOG = LoggerFactory.getLogger(ZdfProgramDetailsProcessor.class);

    @Override
    public void process(ParsedUrl parsedUrl, ZdfProgramDetails doc) {
        LOG.debug("Processing detail xml from url {}: {}", parsedUrl, doc);
    }
}
