package com.github.kaklakariada.mediathek.programs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.mediathek.CrawlerContext;
import com.github.kaklakariada.mediathek.converter.ContentFormat;
import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.model.TvProgram.Builder;
import com.github.kaklakariada.mediathek.processor.DocumentProcessor;
import com.github.kaklakariada.mediathek.programs.ZdfProgramDetailsXml.Details;
import com.github.kaklakariada.mediathek.programs.ZdfProgramDetailsXml.Information;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsXmlProcessor extends DocumentProcessor<ZdfProgramDetailsXml> {
    private static final Logger LOG = LoggerFactory.getLogger(ZdfProgramDetailsXmlProcessor.class);

    public ZdfProgramDetailsXmlProcessor(CrawlerContext context, TvChannel channel) {
        super(context, channel, ContentFormat.XML, ZdfProgramDetailsXml.class);
    }

    @Override
    public void process(ParsedUrl parsedUrl, ZdfProgramDetailsXml doc) {
        LOG.debug("Processing detail xml from url {}: {}", parsedUrl, doc);
        final Details details = doc.getVideo().getDetails();
        final Information info = doc.getVideo().getInfo();

        final String websiteUrl = details.getCurrentPage().startsWith("//") ? "http" + details.getCurrentPage()
                : details.getCurrentPage();
        final Builder programBuilder = newTvProgramBuilder()
                .title(info.getTitle())
                .description(info.getDetail())
                .airtime(details.getAirtime())
                .duration(details.getDuration())
                .websiteUrl(websiteUrl);

        final String jsonUrl = "https://www.zdf.de/ptmd/vod/3sat/" + details.getBasename() + "/1";
        context.getExecutor().executeDownload(jsonUrl,
                new ZdfProgramDetailsJsonProcessor(context, getChannel(), programBuilder));
    }
}
