package com.hyome.hire_radar_backend.domain.repository;

import com.hyome.hire_radar_backend.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByIsActive(Integer isActive);
}
