package org.openschool.controllers;


import org.openschool.entity.CategoryInfoDTO;

import org.openschool.entity.PaginatedResponse;
import org.openschool.entity.ProductInfoDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://productapi:9090/products";

    public ProductViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(value = "category", required = false) String category,
                                 @RequestParam(value = "search", required = false) String search) { // Добавлен параметр search

        String url = baseUrl + "?page=" + page + "&size=" + size;

        if (category != null && !category.isEmpty()) {
            url += "&category=" + category;
        }

        if (search != null && !search.isEmpty()) {
            url += "&search=" + search;
        }
        ResponseEntity<PaginatedResponse<ProductInfoDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginatedResponse<ProductInfoDTO>>() {});
        PaginatedResponse<ProductInfoDTO> body = response.getBody();

        if (body != null) {
            model.addAttribute("category", category);
            model.addAttribute("search", search);
            model.addAttribute("products", body.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", body.getTotalPages());
            model.addAttribute("categories", getAllCategories());
        } else {
            model.addAttribute("products", Collections.emptyList());
        }
        return "products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductInfoDTO product = restTemplate.getForObject(baseUrl + "/" + id, ProductInfoDTO.class);
        model.addAttribute("product", product);
        model.addAttribute("categories", getAllCategories());
        return "product";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute  @Validated ProductInfoDTO productInfoDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));


            redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
            redirectAttributes.addFlashAttribute("product", productInfoDTO);
            return "redirect:/products/" + id;
        }

        try {
            restTemplate.put(baseUrl + "/" + id, productInfoDTO);
        } catch (HttpClientErrorException.Conflict e) {
            redirectAttributes.addFlashAttribute("product", productInfoDTO);
            redirectAttributes.addFlashAttribute("errorMessage", "Product with the same name already exists.");
            return "redirect:/products/" + id;
        }

        return "redirect:/products";
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", ProductInfoDTO.builder().build());
        model.addAttribute("categories", getAllCategories());
        return "productreg";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute @Validated ProductInfoDTO product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/products/create";
        }

        try {
            restTemplate.postForEntity(baseUrl, product, ProductInfoDTO.class);
        } catch (HttpClientErrorException.Conflict e) {
            String errorMessage = e.getResponseBodyAsString();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/products/create";
        }

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}") // Thymeleaf не поддерживает, так как REST
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(baseUrl + "/" + id);
        } catch (HttpClientErrorException.NotFound e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product with ID " + id + " not found");
        }
        return "redirect:/products";
    }

    private List<CategoryInfoDTO> getAllCategories() {
        ResponseEntity<CategoryInfoDTO[]> response = restTemplate.getForEntity("http://productapi:9090/categories", CategoryInfoDTO[].class);
        CategoryInfoDTO[] body = response.getBody();
        if (body != null) {
            return Arrays.asList(body);
        } else {
            return Collections.emptyList();
        }
    }

}
