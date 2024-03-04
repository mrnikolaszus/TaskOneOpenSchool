package org.openschool.config.controllers;

import org.openschool.config.entity.ProductInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:9090/products";

    public ProductViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        ResponseEntity<ProductInfoDTO[]> response = restTemplate.getForEntity(baseUrl, ProductInfoDTO[].class);
        model.addAttribute("products", response.getBody());
        return "products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductInfoDTO product = restTemplate.getForObject(baseUrl + "/" + id, ProductInfoDTO.class);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute  @Validated ProductInfoDTO productInfoDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
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
        return "productreg";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute @Validated ProductInfoDTO product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/products/create";
        }

        try {
            restTemplate.postForEntity(baseUrl, product, ProductInfoDTO.class);
        } catch (HttpClientErrorException.Conflict e) {
            String errorMessage = e.getResponseBodyAsString();
            redirectAttributes.addFlashAttribute("error", errorMessage);
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

}
