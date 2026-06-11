package com.hyome.hire_radar_backend.application.scheduler;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.model.NotificationHistory;
import com.hyome.hire_radar_backend.domain.model.Posting;
import com.hyome.hire_radar_backend.domain.repository.NotificationHistoryRepository;
import com.hyome.hire_radar_backend.infrastructure.notification.DiscordClient;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class DiscordNotificationService {
    private final DiscordClient discordClient;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final FitAnalysisService fitAnalysisService;

    public DiscordNotificationService(DiscordClient discordClient,
                                      NotificationHistoryRepository notificationHistoryRepository,
                                      FitAnalysisService fitAnalysisService) {
        this.discordClient = discordClient;
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.fitAnalysisService = fitAnalysisService;
    }

    public void notifyNewPosting(Company company, Posting posting) {
        if (posting.getId() == null || notificationHistoryRepository.existsByPostingId(posting.getId())) {
            return;
        }

        FitAnalysisService.FitResult fit = fitAnalysisService.analyze(posting);
        String message = """
                [신규 채용공고 발견]
                회사: %s
                공고: %s
                마감일: %s
                적합도: %d%%
                링크: %s
                """.formatted(company.getName(), posting.getTitle(), blankToDash(posting.getDeadline()), fit.score(), posting.getUrl());

        discordClient.send(message);
        notificationHistoryRepository.save(NotificationHistory.builder()
                .postingId(posting.getId())
                .sentAt(OffsetDateTime.now().toString())
                .build());
    }

    private String blankToDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
