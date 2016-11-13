package com.github.kaklakariada.mediathek.programs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.kaklakariada.mediathek.converter.ConverterInput;
import com.github.kaklakariada.mediathek.converter.XmlConverter;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsTest {

    @Test
    public void test() throws IOException {
        final ZdfProgramDetails details = parseFile(
                Paths.get("src/test/resources/com/github/kaklakariada/mediathek/programs/zdf-mediathek-v2.xml"));
        assertThat(details.getVideo().getInfo().getTitle(), equalTo("auslandsjournal extra vom 11.11.2016"));
        assertThat(details.getVideo().getDetails().getCurrentPage(), equalTo("//www.3sat.de/mediathek/?obj=62877"));
    }

    private ZdfProgramDetails parseFile(Path path) throws IOException {
        final XmlConverter<ZdfProgramDetails> converter = new XmlConverter<>(ZdfProgramDetails.class);
        final String content = new String(Files.readAllBytes(path));
        return converter.convert(new ConverterInput(content, ParsedUrl.parse(path.toUri().toURL())));
    }
}
