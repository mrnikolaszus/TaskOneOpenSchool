package org.openschool.controllers;

import org.openschool.entity.CategoryInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/categories")
public class CategoryViewController {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://productapi:9090/categories";

    public CategoryViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        ResponseEntity<CategoryInfoDTO[]> response = restTemplate.getForEntity(baseUrl, CategoryInfoDTO[].class);
        model.addAttribute("categories", response.getBody());
        return "categories";
    }
}
