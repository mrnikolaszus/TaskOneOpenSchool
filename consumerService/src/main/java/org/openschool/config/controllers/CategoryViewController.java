package org.openschool.config.controllers;

import org.openschool.config.entity.CategoryInfoDTO;
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
    private final String baseUrl = "http://localhost:9090/categories";

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
