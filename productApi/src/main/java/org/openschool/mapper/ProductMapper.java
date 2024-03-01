package org.openschool.mapper;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductInfoDTO> {
    @Override
    public Product toEntity(ProductInfoDTO productInfoDTO) {
        var product = new Product();
        product.setId(productInfoDTO.getId());
        product.setName(productInfoDTO.getName());
        product.setDescription(productInfoDTO.getDescription());
        product.setPrice(productInfoDTO.getPrice());
        product.setCategoryId(productInfoDTO.getCategoryId());

        return product;
    }

    @Override
    public ProductInfoDTO toDTO(Product product) {
        return ProductInfoDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .build();
    }
}
