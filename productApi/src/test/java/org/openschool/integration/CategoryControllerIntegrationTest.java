package org.openschool.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openschool.dto.CategoryInfoDTO;
import org.openschool.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long TEST_ID_2 = 2L;
    private final Long TEST_ID_3 = 3L;

    private CategoryInfoDTO createTestCategoryDTO() {
        return CategoryInfoDTO.builder()
                .name("Integration Test Category")
                .build();
    }

    @Test
    public void testCreateCategory() throws Exception {
        CategoryInfoDTO category = createTestCategoryDTO();
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCategoryById() throws Exception {
        mockMvc.perform(get("/categories/{id}", TEST_ID_2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID_2));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryInfoDTO category = createTestCategoryDTO();
        category.setId(TEST_ID_2);
        mockMvc.perform(put("/categories/{id}", TEST_ID_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test Category"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(get("/categories/{id}", TEST_ID_3))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/categories/{id}", TEST_ID_3))
                .andExpect(status().isOk());
        mockMvc.perform(get("/categories/{id}", TEST_ID_3))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFoundException))
                .andExpect(jsonPath("$.message").value("Category with ID " + TEST_ID_3 + " not found"));
    }
}