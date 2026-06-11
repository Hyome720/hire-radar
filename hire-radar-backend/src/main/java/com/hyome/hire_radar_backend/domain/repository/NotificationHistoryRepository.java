package com.hyome.hire_radar_backend.domain.repository;

import com.hyome.hire_radar_backend.domain.model.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
    boolean existsByPostingId(Long postingId);
}
