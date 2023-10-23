package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() throws ProductException, IOException {
        // Arrange
        Product product = new Product();
        MultipartFile imageFile = mock(MultipartFile.class);

        when(productRepository.save(product)).thenReturn(product);
        when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
        when(imageFile.getBytes()).thenReturn(new byte[0]); // Simulate empty file content

        Path directoryPath = Paths.get("C:/Users/milan/WebstormProjects/frontend-webshop-main/img/user-files");
        Files.createDirectories(directoryPath);


        // Act
        Product result = productService.addProduct(product, imageFile);

        // Assert
        assertEquals(product, result);

        // Verify that the file operations are called
        verify(productRepository, times(1)).save(product);
        verify(imageFile, times(1)).getOriginalFilename();
        verify(imageFile, times(1)).getBytes();
    }


    @Test
    void testUpdateProduct() throws ProductException {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        Product result = productService.updateProduct(existingProduct);

        // Assert
        assertEquals(existingProduct, result);

        // Verify repository interactions
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    // Additional test methods for other service operations can be added here.
}
