package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.BackendSandboxThreeApplication;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendSandboxThreeApplication.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testViewAllProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());

        when(productService.viewAllProduct()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/view")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewProductById() throws Exception {
        Long productId = 1L;
        Product product = new Product();

        when(productService.viewProduct(productId)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/view/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // You can add more tests for other controller methods as needed.
}
