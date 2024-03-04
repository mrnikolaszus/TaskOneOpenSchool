package org.openschool.controller;

import org.openschool.dto.ReviewDTO;
import org.openschool.exception.ReviewNotFoundException;
import org.openschool.exception.ValidationApiException;
import org.openschool.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(@RequestBody @Validated ReviewDTO reviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());
        }
        return reviewService.createReview(reviewDTO);
    }

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewDTO getReviewById(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewService.getReviewById(id);
        if (reviewDTO == null) {
            throw new ReviewNotFoundException("Review with ID " + id + " not found");
        }
        return reviewDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO updateReview(@PathVariable Long id, @RequestBody @Validated ReviewDTO reviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());
        }

        ReviewDTO existingReviewDTO = reviewService.getReviewById(id);
        if (existingReviewDTO == null) {
            throw new ReviewNotFoundException("Review with ID " + id + " not found");
        }

        if (!reviewDTO.getId().equals(id)) {
            throw new ValidationApiException("ID in the path does not match ID in the body");
        }

        return reviewService.updateReview(id, reviewDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewService.getReviewById(id);
        if (reviewDTO == null) {
            throw new ReviewNotFoundException("Review with ID " + id + " not found");
        }
        reviewService.deleteReview(id);
    }
}