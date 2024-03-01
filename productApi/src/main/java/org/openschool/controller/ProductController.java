package org.openschool.controller;

import org.openschool.dto.ProductInfoDTO;
import org.openschool.exception.ProductNotFoundException;
import org.openschool.exception.ValidationApiException;
import org.openschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ProductInfoDTO createProduct(@RequestBody @Validated ProductInfoDTO productInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());
        return productService.createProduct(productInfoDTO);
    }

    @GetMapping
    public List<ProductInfoDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductInfoDTO getProductById(@PathVariable Long id) {
        ProductInfoDTO productInfoDTO = productService.getProductById(id);
        if (productInfoDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        return productInfoDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoDTO updateProduct(@PathVariable Long id, @RequestBody @Validated ProductInfoDTO productInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());

        ProductInfoDTO existingProductInfoDTO = productService.getProductById(id);
        if (existingProductInfoDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");

        if (!productInfoDTO.getId().equals(id))
            throw new ValidationApiException("ID in the path does not match ID in the body");

        return productService.updateProduct(id, productInfoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        ProductInfoDTO productInfoDTO = productService.getProductById(id);
        if (productInfoDTO == null)
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        productService.deleteProduct(id);
    }
}
