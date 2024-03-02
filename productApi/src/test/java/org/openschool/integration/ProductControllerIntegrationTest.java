package org.openschool.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openschool.dto.ProductInfoDTO;
import org.openschool.entity.Product;
import org.openschool.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

public class ProductControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    ProductRepository productRepository;

    private final Long TEST_ID_2 = 5L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductInfoDTO createTestProductDTO() {
        return ProductInfoDTO.builder()
                .name("Integration Test Product")
                .description("Test Description")
                .price(100.0)
                .categoryId(1)
                .build();
    }

    private Product createTestProduct() {
        return Product.builder()
                .name("Integration Test Product")
                .description("Test Description")
                .price(100.0)
                .categoryId(1)
                .build();
    }


    @Test
    public void testCreateProduct() throws Exception {
        ProductInfoDTO product = createTestProductDTO();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/{id}", TEST_ID_2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID_2));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductInfoDTO product = createTestProductDTO();
        product.setId(TEST_ID_2);
        mockMvc.perform(put("/products/{id}", TEST_ID_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test Product"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        final Long TEST_ID_3 = 3L;
        mockMvc.perform(delete("/products/{id}", TEST_ID_3))
                .andExpect(status().isOk());
        mockMvc.perform(get("/products/{id}", TEST_ID_3))
                .andExpect(status().isNotFound());
    }
}

