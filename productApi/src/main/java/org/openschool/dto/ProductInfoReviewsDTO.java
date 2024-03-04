package org.openschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductInfoReviewsDTO {
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 1, max = 64, message = "Product name must be between 1 and 64 characters")
    private String name;

    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be a non-negative number")
    private Double price;

    private String category;
    private Integer avgRating;
    private List<ReviewDTO> reviews;
}
