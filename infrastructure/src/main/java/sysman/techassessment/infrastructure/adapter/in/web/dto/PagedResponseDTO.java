package sysman.techassessment.infrastructure.adapter.in.web.dto;

import java.util.List;

public record PagedResponseDTO<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {}