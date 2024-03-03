package org.openschool.service.impl;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.entity.Product;
import org.openschool.exception.ProductExistException;
import org.openschool.exception.ProductNotFoundException;
import org.openschool.mapper.ProductMapper;
import org.openschool.repository.ProductRepository;
import org.openschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductInfoDTO createProduct(ProductInfoDTO productInfoDTO) {
        if(productRepository.existsByName(productInfoDTO.getName())) {
            throw new ProductExistException("A product with the name " + productInfoDTO.getName() + " already exists.");
        }
        Product product = productMapper.toEntity(productInfoDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductInfoDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInfoDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductInfoDTO updateProduct(Long id, ProductInfoDTO productInfoDTO) {
        if(productRepository.existsByNameAndIdNot(productInfoDTO.getName(), id)) {
            throw new ProductExistException("A product with the name " + productInfoDTO.getName() + " already exists.");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        existingProduct.setName(productInfoDTO.getName());
        existingProduct.setDescription(productInfoDTO.getDescription());
        existingProduct.setPrice(productInfoDTO.getPrice());
        existingProduct.setCategoryId(productInfoDTO.getCategoryId());
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
}