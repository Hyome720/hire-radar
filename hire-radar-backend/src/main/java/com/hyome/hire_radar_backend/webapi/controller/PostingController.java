package com.hyome.hire_radar_backend.webapi.controller;

import com.hyome.hire_radar_backend.application.scheduler.PostingService;
import com.hyome.hire_radar_backend.application.scheduler.ScrapingService;
import com.hyome.hire_radar_backend.domain.model.Posting;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postings")
public class PostingController {
    private final PostingService postingService;
    private final ScrapingService scrapingService;

    public PostingController(PostingService postingService, ScrapingService scrapingService) {
        this.postingService = postingService;
        this.scrapingService = scrapingService;
    }

    @GetMapping
    public List<PostingService.PostingSummary> findAll(@RequestParam(required = false) String query,
                                                       @RequestParam(required = false) Long companyId,
                                                       @RequestParam(required = false) Boolean openOnly) {
        return postingService.findPostings(query, companyId, openOnly);
    }

    @PostMapping("/scrape")
    public List<Posting> scrapeNow() {
        return scrapingService.scrapeAllActiveCompanies();
    }
}
