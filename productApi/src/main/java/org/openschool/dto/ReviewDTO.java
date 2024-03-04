package org.openschool.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String text;
    private Integer rate;
}
