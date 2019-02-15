package com.gus.translations.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ScrapperSetting {

    protected static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/45.0.2454.101 Safari/537.36";
    protected static final String REFERRER = "http://www.google.com";
    protected static final String PROXY_HOST = "10.51.55.34";
    protected static final int PROXY_PORT = 8080;
    protected static final int TIMEOUT = 10 * 1000;

    protected List<String> domainList = new ArrayList<>();
    protected List<String> dictionaryList = new ArrayList<>();

    protected Document connectWith(String link) throws IOException {
        Document document = Jsoup.connect(link)
                .proxy(PROXY_HOST, PROXY_PORT)
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .timeout(TIMEOUT)
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .get();
        return document;
    }

}
