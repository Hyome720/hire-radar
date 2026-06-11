package com.hyome.hire_radar_backend.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruitment_company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "career_url", nullable = false)
    private String careerUrl;

    @Column(name = "scraping_type", nullable = false)
    private String scrapingType;

    @Column(name = "check_interval_minutes")
    private Integer checkIntervalMinutes;

    @Column(name = "is_active")
    private Integer isActive;
}
