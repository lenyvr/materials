package sysman.techassessment.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaterialListItemDTO(
        Integer id,
        String name,
        String description,
        String type,
        Double price,
        LocalDateTime buyDate,
        LocalDateTime soldDate,
        CityDto city,
        String state
) {
}
