package sysman.techassessment.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import sysman.techassessment.domain.model.MaterialState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaterialResponseDto (
    Integer id,
    String name,
    String description,
    String type,
    BigDecimal price,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime buyDate,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime soldDate,
    CityDto city,
    MaterialState state)
{}
