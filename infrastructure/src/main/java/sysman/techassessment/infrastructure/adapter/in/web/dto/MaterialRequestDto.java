package sysman.techassessment.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sysman.techassessment.domain.model.MaterialState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaterialRequestDto(

        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must not exceed 30 characters")
        String name,
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String description,

        @NotBlank(message = "Type is required")
        String type,

        @NotNull(message = "Price is required")
        BigDecimal price,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime buyDate,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime soldDate,

        @NotBlank(message = "City code is required")
        @Size(max = 10, message = "Name must not exceed 10 characters")
        String cityCode,

        MaterialState state) {
}
