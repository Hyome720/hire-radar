package com.hyome.hire_radar_backend.webapi.controller;

import com.hyome.hire_radar_backend.domain.model.Company;
import com.hyome.hire_radar_backend.domain.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        if (company.getScrapingType() == null || company.getScrapingType().isBlank()) {
            company.setScrapingType("STATIC");
        }
        if (company.getCheckIntervalMinutes() == null) {
            company.setCheckIntervalMinutes(60);
        }
        if (company.getIsActive() == null) {
            company.setIsActive(1);
        }
        return companyRepository.save(company);
    }

    @PatchMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
        if (request.getName() != null) {
            company.setName(request.getName());
        }
        if (request.getCareerUrl() != null) {
            company.setCareerUrl(request.getCareerUrl());
        }
        if (request.getScrapingType() != null) {
            company.setScrapingType(request.getScrapingType());
        }
        if (request.getCheckIntervalMinutes() != null) {
            company.setCheckIntervalMinutes(request.getCheckIntervalMinutes());
        }
        if (request.getIsActive() != null) {
            company.setIsActive(request.getIsActive());
        }
        return companyRepository.save(company);
    }
}
