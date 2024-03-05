package org.openschool.service.impl;


import org.openschool.dto.ReviewDTO;
import org.openschool.entity.Review;
import org.openschool.exception.ReviewNotFoundException;
import org.openschool.mapper.ReviewMapper;
import org.openschool.repository.ReviewRepository;
import org.openschool.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(reviewMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review with ID " + id + " not found");
        }
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setId(id);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review with ID " + id + " not found");
        }
        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(reviewMapper::toDTO).collect(Collectors.toList());
    }
}