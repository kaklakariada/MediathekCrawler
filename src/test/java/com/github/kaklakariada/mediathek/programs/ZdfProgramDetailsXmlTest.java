package com.github.kaklakariada.mediathek.programs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.kaklakariada.mediathek.converter.ContentFormat;
import com.github.kaklakariada.mediathek.converter.Converter;
import com.github.kaklakariada.mediathek.converter.ConverterFactory;
import com.github.kaklakariada.mediathek.converter.ConverterInput;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsXmlTest {

    @Test
    public void test() throws IOException {
        final ZdfProgramDetailsXml details = parseFile(
                Paths.get("src/test/resources/com/github/kaklakariada/mediathek/programs/zdf-mediathek-v2.xml"));
        assertThat(details.getVideo().getInfo().getTitle(), equalTo("auslandsjournal extra vom 11.11.2016"));
        assertThat(details.getVideo().getDetails().getCurrentPage(), equalTo("//www.3sat.de/mediathek/?obj=62877"));
        assertThat(details.getVideo().getDetails().getBasename(), equalTo("161111_sendung_ajextra"));
    }

    private ZdfProgramDetailsXml parseFile(Path path) throws IOException {
        final Converter<ZdfProgramDetailsXml> converter = new ConverterFactory().createConverter(ContentFormat.XML,
                ZdfProgramDetailsXml.class);
        final String content = new String(Files.readAllBytes(path));
        return converter.convert(new ConverterInput(content, ParsedUrl.parse(path.toUri().toURL())));
    }
}
