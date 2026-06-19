package com.hyome.hire_radar_backend.infrastructure.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PageFetchService {

    public String fetchHtml(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("채용 페이지 URL이 필요합니다.");
        }

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; HireRadar/1.0)")
                    .timeout(15_000)
                    .ignoreHttpErrors(false)
                    .get();

            document.select("script, style, noscript").remove();

            return document.body() == null
                    ? ""
                    : document.body().html();
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "채용 페이지를 가져오지 못했습니다:" + url,
                    exception
            );
        }
    }
}
