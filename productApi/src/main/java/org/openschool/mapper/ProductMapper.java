package org.openschool.mapper;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.dto.ProductInfoReviewsDTO;
import org.openschool.dto.ReviewDTO;
import org.openschool.entity.Product;
import org.openschool.entity.Review;
import org.openschool.repository.CategoryRepository;
import org.openschool.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper implements Mapper<Product, ProductInfoReviewsDTO> {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product toEntity(ProductInfoReviewsDTO productInfoReviewsDTO) {
        Long categoryId = categoryService.getCategoryByName(productInfoReviewsDTO.getCategory()).getId();

        Product product = new Product();
        product.setId(productInfoReviewsDTO.getId());
        product.setName(productInfoReviewsDTO.getName());
        product.setDescription(productInfoReviewsDTO.getDescription());
        product.setPrice(productInfoReviewsDTO.getPrice());
        product.setCategoryId(categoryId.intValue());


        return product;
    }

    @Override
    public ProductInfoReviewsDTO toDTO(Product product) {
        ProductInfoReviewsDTO productInfoReviewsDTO = ProductInfoReviewsDTO.builder().build();
        productInfoReviewsDTO.setId(product.getId());
        productInfoReviewsDTO.setName(product.getName());
        productInfoReviewsDTO.setDescription(product.getDescription());
        productInfoReviewsDTO.setPrice(product.getPrice());

        productInfoReviewsDTO.setCategory(categoryService.getCategoryById(product.getCategoryId().longValue()).getName());
        Set<Review> reviews = Optional.ofNullable(product.getReviews()).orElse(Collections.emptySet());
        productInfoReviewsDTO.setAvgRating(calculateAverageRating(reviews));

        return productInfoReviewsDTO;
    }

    private Integer calculateAverageRating(Set<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0;
        }
        int totalRating = reviews.stream().mapToInt(Review::getRate).sum();
        return totalRating / reviews.size();
    }


    private List<ReviewDTO> mapReviewsToDTO(Set<Review> reviews) {
        return reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}

