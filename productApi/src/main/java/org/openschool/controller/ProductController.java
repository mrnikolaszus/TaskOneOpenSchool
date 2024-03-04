package org.openschool.controller;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.dto.ProductInfoReviewsDTO;
import org.openschool.exception.ProductNotFoundException;
import org.openschool.exception.ValidationApiException;
import org.openschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInfoReviewsDTO createProduct(@RequestBody @Validated ProductInfoReviewsDTO productInfoReviewsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());
        return productService.createProduct(productInfoReviewsDTO);
    }

//    @GetMapping
//    public List<ProductInfoReviewsDTO> getAllProducts() {
//        return productService.getAllProducts();
//    }

    @GetMapping
    public ResponseEntity<Page<ProductInfoReviewsDTO>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "category", required = false) String category) {
        Page<ProductInfoReviewsDTO> productPage;

        if (category != null && !category.isEmpty()) {
            productPage = productService.getAllProductsByCategory(page, size, category);
        } else {
            productPage = productService.getAllProducts(page, size);
        }

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ProductInfoReviewsDTO getProductById(@PathVariable Long id) {
        ProductInfoReviewsDTO productInfoReviewsDTO = productService.getProductById(id);
        if (productInfoReviewsDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        return productInfoReviewsDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoReviewsDTO updateProduct(@PathVariable Long id, @RequestBody @Validated ProductInfoReviewsDTO productInfoReviewsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());

        ProductInfoReviewsDTO existingProductInfoDTO = productService.getProductById(id);
        if (existingProductInfoDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");

        if (!productInfoReviewsDTO.getId().equals(id))
            throw new ValidationApiException("ID in the path does not match ID in the body");

        return productService.updateProduct(id, productInfoReviewsDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        ProductInfoReviewsDTO productInfoReviewsDTO = productService.getProductById(id);
        if (productInfoReviewsDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        productService.deleteProduct(id);
    }
}
