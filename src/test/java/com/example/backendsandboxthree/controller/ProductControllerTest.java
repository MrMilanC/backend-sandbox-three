package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.service.CategoryService;
import com.example.backendsandboxthree.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewAllProduct() throws ProductException {
        List<Product> products = new ArrayList<>();
        // Adding sample products to the list

        when(productService.viewAllProduct()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.viewAllProduct();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testViewProductById() throws ProductException {
        Long productId = 1L;
        Product product = new Product(); // Creating a sample product

        when(productService.viewProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.viewProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testViewProductByCategoryId() throws ProductException {
        Long categoryId = 1L;
        List<Product> products = new ArrayList<>();
        // Adding sample products to the list

        when(categoryService.viewProductByCategory(categoryId)).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.viewProductByCategoryId(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }
}
