package org.openschool.mapper;

import org.openschool.dto.ReviewDTO;
import org.openschool.entity.Product;
import org.openschool.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements Mapper<Review, ReviewDTO> {

    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setText(reviewDTO.getText());
        review.setRate(reviewDTO.getRate());
        if (reviewDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(reviewDTO.getProductId());
            review.setProduct(product);
        }
        return review;
    }

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(review.getId())
                .text(review.getText())
                .rate(review.getRate())
                .productId(review.getProduct() != null ? review.getProduct().getId() : null)
                .build();
        return reviewDTO;
    }
}