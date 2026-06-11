package com.hyome.hire_radar_backend.application.scheduler;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.model.Posting;
import com.hyome.hire_radar_backend.domain.repository.CompanyRepository;
import com.hyome.hire_radar_backend.domain.repository.PostingRepository;
import com.hyome.hire_radar_backend.infrastructure.scraper.ScraperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {
    private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);

    private final CompanyRepository companyRepository;
    private final PostingRepository postingRepository;
    private final ScraperFactory scraperFactory;
    private final DiscordNotificationService notificationService;

    public ScrapingService(CompanyRepository companyRepository,
                           PostingRepository postingRepository,
                           ScraperFactory scraperFactory,
                           DiscordNotificationService notificationService) {
        this.companyRepository = companyRepository;
        this.postingRepository = postingRepository;
        this.scraperFactory = scraperFactory;
        this.notificationService = notificationService;
    }

    @Transactional
    public List<Posting> scrapeAllActiveCompanies() {
        List<Posting> saved = new ArrayList<>();

        for (Company company : companyRepository.findByIsActive(1)) {
            try {
                saved.addAll(scrapeCompany(company));
            } catch (Exception ex) {
                log.warn("Failed to scrape company {}: {}", company.getName(), ex.getMessage());
            }
        }

        return saved;
    }

    @Transactional
    public List<Posting> scrapeCompany(Company company) {
        List<Posting> saved = new ArrayList<>();

        List<Posting> scrapedPostings = scraperFactory
                .get(company)
                .scrape(company);

        for (Posting posting : scrapedPostings) {
            if (posting.getUrl() == null || posting.getUrl().isBlank()) {
                continue;
            }

            if (postingRepository.existsByUrl(posting.getUrl())) {
                continue;
            }

            if (posting.getCreatedAt() == null || posting.getCreatedAt().isBlank()) {
                posting.setCreatedAt(OffsetDateTime.now().toString());
            }

            posting.setCompanyId(company.getId());

            Posting newPosting = postingRepository.save(posting);
            notificationService.notifyNewPosting(company, newPosting);
            saved.add(newPosting);
        }

        return saved;
    }
}
