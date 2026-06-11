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
import java.util.List;

@Component

public class KbDataSystemScraper implements DynamicScraper {
    @Override
    public boolean supports(Company company) {
        return company.getName() != null && company.getName().contains("KB데이타시스템");
    }

    @Override
    public List<Posting> scrape(Company company) {
        List<Posting> postings = new ArrayList<>();

        try {
            Document document = Jsoup.connect(company.getCareerUrl())
                    .userAgent("Mozilla/5.0 hire-radar")
                    .timeout(10000)
                    .get();

            for (Element row : document.select("a[href]")) {
                String title = row.text().trim().replaceAll("\\s+", " ");

                if (!isRecruitmentPosting(title)) {
                    continue;
                }

                String url = row.absUrl("href");
                if (url == null || url.isBlank()) {
                    url = company.getCareerUrl();
                }

                postings.add(Posting.builder()
                        .companyId(company.getId())
                        .title(trim(title, 180))
                        .status("OPEN")
                        .postedDate(LocalDate.now().toString())
                        .deadline("")
                        .url(url)
                        .description(buildDescription(title))
                        .createdAt(OffsetDateTime.now().toString())
                        .build());
            }
        }
        catch (Exception ignored) {
            return List.of();
        }

        return postings.stream()
                .distinct()
                .limit(30)
                .toList();
    }

    private boolean isRecruitmentPosting(String title) {
        if (title == null || title.isBlank()) {
            return false;
        }

        return title.contains("채용") ||
                title.contains("모집") ||
                title.contains("개발") ||
                title.toLowerCase().contains("developer") ||
                title.toLowerCase().contains("engineer");
    }

    private String buildDescription(String title) {
        return "KB데이타시스템 채굥 공고 : " + title;
    }

    private String trim(String value, int maxLength) {
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }
}
