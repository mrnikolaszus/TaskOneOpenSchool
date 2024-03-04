package org.openschool.mapper;

import org.openschool.dto.ReviewDTO;
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

        return review;
    }

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder().build();
        reviewDTO.setId(review.getId());
        reviewDTO.setText(review.getText());
        reviewDTO.setRate(review.getRate());

        return reviewDTO;
    }
}
