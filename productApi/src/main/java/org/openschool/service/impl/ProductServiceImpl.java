package org.openschool.service.impl;

import org.openschool.dto.CategoryInfoDTO;
import org.openschool.dto.ProductInfoReviewsDTO;
import org.openschool.entity.Product;
import org.openschool.exception.CategoryNotFoundException;
import org.openschool.exception.ProductExistException;
import org.openschool.exception.ProductNotFoundException;
import org.openschool.mapper.ProductMapper;
import org.openschool.repository.ProductRepository;
import org.openschool.service.CategoryService;
import org.openschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<ProductInfoReviewsDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductInfoReviewsDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    @Transactional
    public ProductInfoReviewsDTO createProduct(ProductInfoReviewsDTO productInfoReviewsDTO) {
        if (productRepository.existsByName(productInfoReviewsDTO.getName())) {
            throw new ProductExistException("A product with the name " + productInfoReviewsDTO.getName() + " already exists.");
        }
        Product product = productMapper.toEntity(productInfoReviewsDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductInfoReviewsDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        return productMapper.toDTO(product);
    }


    @Override
    @Transactional
    public ProductInfoReviewsDTO updateProduct(Long id, ProductInfoReviewsDTO productInfoReviewsDTO) {

        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryByName(productInfoReviewsDTO.getCategory());
        if (categoryInfoDTO == null) {
            throw new CategoryNotFoundException("Category '" + productInfoReviewsDTO.getCategory() + "' not found");
        }

        Long categoryId = categoryInfoDTO.getId();

        if (productRepository.existsByNameAndIdNot(productInfoReviewsDTO.getName(), id)) {
            throw new ProductExistException("A product with the name " + productInfoReviewsDTO.getName() + " already exists.");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        existingProduct.setName(productInfoReviewsDTO.getName());
        existingProduct.setDescription(productInfoReviewsDTO.getDescription());
        existingProduct.setPrice(productInfoReviewsDTO.getPrice());
        existingProduct.setCategoryId(categoryId.intValue());
        existingProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductInfoReviewsDTO> getAllProductsByCategory(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryName(category, pageable).map(productMapper::toDTO);
    }



}