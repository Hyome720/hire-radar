package com.hyome.hire_radar_backend.webapi.dto.collection;

import java.util.List;

public record CompanyCollectionDto(
        Long companyId,
        String companyName,
        String sourceUrl,
        List<CollectedLinkDto> links,
        String errorMessage
) {
}
