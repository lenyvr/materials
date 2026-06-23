package sysman.techassessment.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sysman.techassessment.domain.model.MaterialState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateMaterialRequestDTO(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        @NotBlank(message = "Type is required")
        String type,
        @NotNull(message = "Price is required")
        BigDecimal price,
        @JsonProperty("buy_date")
        LocalDateTime buyDate,
        @JsonProperty("sold_date")
        LocalDateTime soldDate,
        MaterialState state,
        @NotBlank(message = "City code is required")
        @JsonProperty("city_code")
        String cityCode
) {
}
