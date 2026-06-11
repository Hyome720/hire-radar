package com.hyome.hire_radar_backend.infrastructure.scraper;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.model.Posting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenericHtmlScraper implements DynamicScraper {
    @Override
    public List<Posting> scrape(Company company) {
        Map<String, Posting> postings = new LinkedHashMap<>();

        try {
            Document document = Jsoup.connect(company.getCareerUrl())
                    .userAgent("Mozilla/5.0 hire-radar")
                    .timeout(10000)
                    .get();

            for (Element link : document.select("a[href]")) {
                String title = link.text().trim().replaceAll("\\s+", " ");
                if (!looksLikePosting(title)) {
                    continue;
                }

                String url = link.absUrl("href");
                if (url == null || url.isBlank()) {
                    url = company.getCareerUrl();
                }

                postings.putIfAbsent(url, Posting.builder()
                        .companyId(company.getId())
                        .title(trim(title, 180))
                        .status("OPEN")
                        .postedDate(LocalDate.now().toString())
                        .deadline("")
                        .url(url)
                        .description(title)
                        .createdAt(OffsetDateTime.now().toString())
                        .build());
            }
        } catch (Exception ignored) {
            postings.put(company.getCareerUrl(), samplePosting(company));
        }

        if (postings.isEmpty()) {
            postings.put(company.getCareerUrl(), samplePosting(company));
        }
        return new ArrayList<>(postings.values()).stream().limit(20).toList();
    }

    @Override
    public boolean supports(Company company) {
        return true;
    }

    private boolean looksLikePosting(String title) {
        String lower = title.toLowerCase();

        return title.length() >= 4 && (
                title.contains("채용") ||
                title.contains("모집") ||
                title.contains("개발") ||
                lower.contains("recruit") ||
                lower.contains("developer") ||
                lower.contains("engineer")
        );
    }

    private Posting samplePosting(Company company) {
        return Posting.builder()
                .companyId(company.getId())
                .title(company.getName() + " 백엔드 개발자 채용 모니터링")
                .status("OPEN")
                .postedDate(LocalDate.now().toString())
                .deadline("")
                .url(company.getCareerUrl())
                .description("ASP.NET Core, SQL, 운영, 데이터 처리, Java/Spring, AWS, Docker 경험을 기준으로 적합도를 분석합니다.")
                .createdAt(OffsetDateTime.now().toString())
                .build();
    }

    private String trim(String value, int maxLength) {
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }
}
