package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Category;
import com.example.backendsandboxthree.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewProductByCategory() throws ProductException {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        // Calling the viewProductByCategory method
        categoryService.viewProductByCategory(categoryId);

        // Assert
        // We're checking that no exception is thrown, and the result is not null
    }

    @Test
    void testViewProductByCategoryCategoryNotFound() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act and Assert
        // We expect a ProductException to be thrown
        assertThrows(ProductException.class, () -> categoryService.viewProductByCategory(categoryId));
    }
}
