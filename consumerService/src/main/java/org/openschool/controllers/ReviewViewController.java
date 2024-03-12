package org.openschool.config.controllers;

import org.openschool.config.entity.ReviewDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/products/{productId}/reviews")
public class ReviewViewController {

    private final RestTemplate restTemplate;
    private final String reviewBaseUrl = "http://productapi:9090/reviews";

    public ReviewViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getProductReviews(@PathVariable Long productId, Model model) {
        String url = reviewBaseUrl + "/product/" + productId;
        ResponseEntity<List<ReviewDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> reviews = response.getBody();

        model.addAttribute("reviews", reviews != null ? reviews : Collections.emptyList());
        model.addAttribute("productId", productId);
        return "reviews";
    }

    @GetMapping("/add")
    public String addReview(@PathVariable Long productId,
                            @ModelAttribute ReviewDTO newReview,
                            RedirectAttributes redirectAttributes) {
        String url = reviewBaseUrl + "/product/" + productId;
        newReview.setId(null);
        restTemplate.postForEntity(url, newReview, ReviewDTO.class);
        redirectAttributes.addFlashAttribute("successMessage", "Review successfully added");
        return "redirect:/products/" + productId + "/reviews";
    }

    @GetMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long productId,
                               @PathVariable Long reviewId,
                               RedirectAttributes redirectAttributes) {
        String url = reviewBaseUrl + "/" + reviewId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        redirectAttributes.addFlashAttribute("successMessage", "Review successfully deleted");
        return "redirect:/products/" + productId + "/reviews";
    }
}