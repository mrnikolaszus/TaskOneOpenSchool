package org.openschool.service;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.dto.ProductInfoReviewsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductInfoReviewsDTO createProduct(ProductInfoReviewsDTO productInfoReviewsDTO);
    ProductInfoReviewsDTO getProductById(Long id);
    Page<ProductInfoReviewsDTO> getAllProducts(int page, int size);
    ProductInfoReviewsDTO updateProduct(Long id, ProductInfoReviewsDTO productInfoReviewsDTO);
    void deleteProduct(Long id);

    Page<ProductInfoReviewsDTO> getAllProductsByCategory(int page, int size, String category);

}