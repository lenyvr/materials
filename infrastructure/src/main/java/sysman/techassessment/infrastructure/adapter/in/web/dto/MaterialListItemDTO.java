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
        @JsonProperty("buy_date")
        LocalDateTime buyDate,
        @JsonProperty("sold_date")
        LocalDateTime soldDate,
        String city,
        String state
) {
}
