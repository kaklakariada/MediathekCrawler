package com.github.kaklakariada.mediathek.download;

import com.github.kaklakariada.mediathek.model.TvChannel;
import com.github.kaklakariada.mediathek.util.ParsedUrl;

public interface UrlTask extends Runnable {
    ParsedUrl getUrl();

    TvChannel getChannel();
}
