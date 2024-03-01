package org.openschool.service;

import org.openschool.dto.ProductInfoDTO;

import java.util.List;

public interface ProductService {
    ProductInfoDTO createProduct(ProductInfoDTO productInfoDTO);
    ProductInfoDTO getProductById(Long id);
    List<ProductInfoDTO> getAllProducts();
    ProductInfoDTO updateProduct(Long id, ProductInfoDTO productInfoDTO);
    void deleteProduct(Long id);
}