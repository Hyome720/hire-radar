package com.hyome.hire_radar_backend.application.scheduler;

import com.hyome.hire_radar_backend.domain.model.Posting;
import com.hyome.hire_radar_backend.domain.repository.PostingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingService {
    private final PostingRepository postingRepository;
    private final FitAnalysisService fitAnalysisService;

    public PostingService(PostingRepository postingRepository, FitAnalysisService fitAnalysisService) {
        this.postingRepository = postingRepository;
        this.fitAnalysisService = fitAnalysisService;
    }

    public List<PostingSummary> findPostings(String query, Long companyId, Boolean openOnly) {
        return postingRepository.findAllByOrderByCreatedAtDesc().stream()
                .filter(posting -> companyId == null || companyId.equals(posting.getCompanyId()))
                .filter(posting -> !Boolean.TRUE.equals(openOnly) || "OPEN".equalsIgnoreCase(posting.getStatus()))
                .filter(posting -> query == null || query.isBlank() ||
                        posting.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        safe(posting.getDescription()).toLowerCase().contains(query.toLowerCase()))
                .map(posting -> {
                    FitAnalysisService.FitResult fit = fitAnalysisService.analyze(posting);
                    return new PostingSummary(posting, fit.score(), fit.reasons());
                })
                .toList();
    }

    public record PostingSummary(Posting posting, int fitScore, List<String> fitReasons) {
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
