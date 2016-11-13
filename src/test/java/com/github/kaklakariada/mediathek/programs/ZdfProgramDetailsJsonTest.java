package com.github.kaklakariada.mediathek.programs;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.kaklakariada.mediathek.converter.ConverterInput;
import com.github.kaklakariada.mediathek.converter.JsonConverter;
import com.github.kaklakariada.mediathek.programs.ZdfProgramDetailsJson.Formitaet;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public class ZdfProgramDetailsJsonTest {

    @Test
    public void test() throws IOException {
        final ZdfProgramDetailsJson json = parseFile(
                Paths.get("src/test/resources/com/github/kaklakariada/mediathek/programs/zdf-mediathek.json"));
        assertThat(json.getFormitaeten(), hasSize(34));

        final Formitaet formitaet0 = json.getFormitaeten().get(0);
        assertThat(formitaet0.getQuality(), equalTo("high"));
        assertThat(formitaet0.getType(), equalTo("h264_aac_mp4_http_na_na"));
        assertThat(formitaet0.getFacets(), contains("hbbtv"));

        assertThat(formitaet0.getPlayouts().keySet(), hasSize(1));
        assertThat(formitaet0.getPlayouts().get("main").getUris(), contains(
                "http://tvdl.zdf.de/none/3sat/16/11/161111_sendung_ajextra/1/161111_sendung_ajextra_2328k_p35v13.mp4"));

    }

    private ZdfProgramDetailsJson parseFile(Path path) throws IOException {
        final JsonConverter<ZdfProgramDetailsJson> converter = new JsonConverter<>(ZdfProgramDetailsJson.class);
        final String content = new String(Files.readAllBytes(path));
        return converter.convert(new ConverterInput(content, ParsedUrl.parse(path.toUri().toURL())));
    }
}
