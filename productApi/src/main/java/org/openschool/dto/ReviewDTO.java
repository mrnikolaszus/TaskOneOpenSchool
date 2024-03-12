package org.openschool.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ReviewDTO {
    private Long id;

    private String text;

    @Min(value = 0, message = "Rate must be between 0 and 5")
    @Max(value = 5, message = "Rate must be between 0 and 5")
    private Integer rate;
    private Long productId;
}
