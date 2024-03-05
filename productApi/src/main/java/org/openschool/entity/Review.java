package org.openschool.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity {

    @Column(length = 255)
    private String text;

    @Min(value = 0, message = "Rate must be between 0 and 5")
    @Max(value = 5, message = "Rate must be between 0 and 5")
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}