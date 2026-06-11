package com.hyome.hire_radar_backend.infrastructure.scraper;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.model.Posting;

import java.util.List;

public interface DynamicScraper {
    List<Posting> scrape(Company company);
    boolean supports(Company company);
}
