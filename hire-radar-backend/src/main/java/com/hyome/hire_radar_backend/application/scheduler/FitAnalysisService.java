package com.hyome.hire_radar_backend.application.scheduler;

import com.hyome.hire_radar_backend.domain.model.Posting;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class FitAnalysisService {
    private final Map<String, Integer> profileWeights = new LinkedHashMap<>();

    public FitAnalysisService() {
        profileWeights.put("backend", 10);
        profileWeights.put("백엔드", 10);
        profileWeights.put("asp.net", 14);
        profileWeights.put("mssql", 12);
        profileWeights.put("sql", 12);
        profileWeights.put("대용량", 10);
        profileWeights.put("운영", 10);
        profileWeights.put("java", 8);
        profileWeights.put("spring", 8);
        profileWeights.put("aws", 8);
        profileWeights.put("docker", 8);
    }

    public FitResult analyze(Posting posting) {
        String target = ((posting.getTitle() == null ? "" : posting.getTitle()) + " " +
                (posting.getDescription() == null ? "" : posting.getDescription())).toLowerCase(Locale.ROOT);
        int score = 35;
        List<String> reasons = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : profileWeights.entrySet()) {
            if (target.contains(entry.getKey())) {
                score += entry.getValue();
                reasons.add(entry.getKey() + " match");
            }
        }

        if (reasons.isEmpty()) {
            reasons.add("General backend monitoring target");
        }

        return new FitResult(Math.min(score, 100), reasons);
    }

    public record FitResult(int score, List<String> reasons) {
    }
}
