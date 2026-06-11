package com.hyome.hire_radar_backend.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruitment_posting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String status;

    @Column(name = "posted_date")
    private String postedDate;

    private String deadline;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private String createdAt;
}
