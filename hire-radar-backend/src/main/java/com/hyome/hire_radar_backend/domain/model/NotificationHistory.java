package com.hyome.hire_radar_backend.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posting_id", nullable = false)
    private Long postingId;

    @Column(name = "sent_at", nullable = false)
    private String sentAt;
}
