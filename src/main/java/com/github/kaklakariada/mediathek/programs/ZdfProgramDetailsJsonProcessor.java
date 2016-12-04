package com.github.kaklakariada.mediathek.programs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.converter.ContentFormat;
import com.github.kaklakariada.mediathek.model.Resolution;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.model.TvProgram.Builder;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.programs.ZdfProgramDetailsJson.Formitaet;
import com.github.kaklakariada.mediathek.util.Pair;
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
        doc.getFormitaeten().stream()
                .filter(f -> "h264_aac_mp4_http_na_na".equals(f.getType()))
                .filter(f -> f.getFacets().contains("progressive"))
                .map(f -> new Pair<>(getResolution(f),
                        f.getPlayouts().get("main").getUris().stream().findFirst().orElse(null)))
                .filter(p -> p.getSecond() != null)
                .forEach(this::getFileSize);
    }

    private Resolution getResolution(Formitaet f) {
        switch (f.getQuality()) {
        case "low":
            return Resolution.SMALL;
        case "high":
            return Resolution.MEDIUM;
        case "veryhigh":
            return Resolution.HIGH;
        default:
            throw new IllegalArgumentException("Unknown quality '" + f.getQuality() + "'");
        }
    }

    private void getFileSize(Pair<Resolution, String> url) {
        context.getExecutor().executeGetContentLength(getChannel(), url.getSecond(), size -> {
            programBuilder.addDownloadUrl(url.getFirst(), url.getSecond(), size);
            context.add(programBuilder.build());
        });
    }
}
