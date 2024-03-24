package org.openschool.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryInfoDTO {
    private Long id;
    private String name;
}
