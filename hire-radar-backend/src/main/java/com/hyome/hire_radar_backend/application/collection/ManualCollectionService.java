package com.hyome.hire_radar_backend.application.collection;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.repository.CompanyRepository;
import com.hyome.hire_radar_backend.infrastructure.web.PageFetchService;
import com.hyome.hire_radar_backend.webapi.dto.collection.CollectedLinkDto;
import com.hyome.hire_radar_backend.webapi.dto.collection.CompanyCollectionDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManualCollectionService {

    private static final int MAX_LINKS_PER_COMPANY = 100;

    private final CompanyRepository companyRepository;
    private final PageFetchService pageFetchService;

    public ManualCollectionService(
            CompanyRepository companyRepository,
            PageFetchService pageFetchService
    ) {
        this.companyRepository = companyRepository;
        this.pageFetchService = pageFetchService;
    }

    public List<CompanyCollectionDto> collectAllActiveCompanies() {
        List<CompanyCollectionDto> results = new ArrayList<>();

        for (Company company : companyRepository.findByIsActive(1)) {
            results.add(collectCompany(company));
        }

        return results;
    }

    private CompanyCollectionDto collectCompany(Company company) {
        try {
            String html = pageFetchService.fetchHtml(company.getCareerUrl());

            Document document = Jsoup.parse(html, company.getCareerUrl());

            Map<String, CollectedLinkDto> uniqueLinks = new LinkedHashMap<>();

            for (Element link : document.select("a[href]")) {
                String title = normalize(link.text());

                if (title.isBlank()) {
                    continue;
                }

                String url = link.absUrl("href");

                if (!isHttpUrl(url)) {
                    continue;
                }

                String context = link.parent() == null
                        ? title
                        : normalize(link.parent().text());

                context = limitLength(context, 300);

                uniqueLinks.putIfAbsent(
                        url,
                        new CollectedLinkDto(title, url, context)
                );
            }

            List<CollectedLinkDto> links = uniqueLinks.values()
                    .stream()
                    .limit(MAX_LINKS_PER_COMPANY)
                    .toList();

            return new CompanyCollectionDto(
                    company.getId(),
                    company.getName(),
                    company.getCareerUrl(),
                    links,
                    null
            );
        } catch (RuntimeException exception) {
            return new CompanyCollectionDto(
                    company.getId(),
                    company.getName(),
                    company.getCareerUrl(),
                    List.of(),
                    exception.getMessage()
            );
        }
    }

    private boolean isHttpUrl(String url) {
        return url != null
                && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private String limitLength(String value, int maxLength) {
        return value.length() <= maxLength
                ? value
                : value.substring(0, maxLength);
    }
}
