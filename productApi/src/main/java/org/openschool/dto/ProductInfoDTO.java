package org.openschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class ProductInfoDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;
}
