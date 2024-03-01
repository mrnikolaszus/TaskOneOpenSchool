package org.openschool.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity {


    private String text;

    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}