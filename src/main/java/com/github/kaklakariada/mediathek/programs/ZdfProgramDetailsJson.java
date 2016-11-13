package com.github.kaklakariada.mediathek.programs;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZdfProgramDetailsJson {

    @JsonProperty("formitaeten")
    private List<Formitaet> formitaeten;

    public List<Formitaet> getFormitaeten() {
        return formitaeten;
    }

    @Override
    public String toString() {
        return "ZdfProgramDetailsJson [formitaeten=" + formitaeten + "]";
    }

    public static class Formitaet {
        @JsonProperty("type")
        private String type;
        @JsonProperty("quality")
        private String quality;
        @JsonProperty("facets")
        private List<String> facets;
        @JsonProperty("playouts")
        private Map<String, Playout> playouts;

        public String getType() {
            return type;
        }

        public String getQuality() {
            return quality;
        }

        public List<String> getFacets() {
            return facets;
        }

        public Map<String, Playout> getPlayouts() {
            return playouts;
        }

        @Override
        public String toString() {
            return "Formitaet [type=" + type + ", quality=" + quality + ", facets=" + facets + ", playouts=" + playouts
                    + "]";
        }
    }

    public static class Playout {
        @JsonProperty("uris")
        private List<String> uris;

        public List<String> getUris() {
            return uris;
        }

        @Override
        public String toString() {
            return "Playout [uris=" + uris + "]";
        }
    }
}
