package com.hyome.hire_radar_backend.infrastructure.scraper;

import com.hyome.hire_radar_backend.domain.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScraperFactory {
    private final List<DynamicScraper> scrapers;
    private final GenericHtmlScraper genericHtmlScraper;

    public ScraperFactory(List<DynamicScraper> scrapers, GenericHtmlScraper genericHtmlScraper) {
        this.scrapers = scrapers;
        this.genericHtmlScraper = genericHtmlScraper;
    }

    public DynamicScraper get(Company company) {
        return scrapers.stream()
                .filter(scraper -> !(scraper instanceof GenericHtmlScraper))
                .filter(scraper -> scraper.supports(company))
                .findFirst()
                .orElse(genericHtmlScraper);
    }
}
