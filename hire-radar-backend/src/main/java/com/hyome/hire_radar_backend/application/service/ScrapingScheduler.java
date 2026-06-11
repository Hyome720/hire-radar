package com.hyome.hire_radar_backend.application.service;

import com.hyome.hire_radar_backend.application.scheduler.ScrapingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapingScheduler {
    private final ScrapingService scrapingService;

    public ScrapingScheduler(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Scheduled(fixedDelayString = "${scraping.fixed-delay-ms:3600000}", initialDelayString = "${scraping.initial-delay-ms:15000}")
    public void run() {
        scrapingService.scrapeAllActiveCompanies();
    }
}
