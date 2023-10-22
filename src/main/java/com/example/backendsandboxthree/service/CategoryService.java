package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Category;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAllCategory(){return categoryRepository.findAll();}
    public void addCategory(Category category){categoryRepository.save(category);}
    public void removeCategoryById(Long id){categoryRepository.deleteById(id);}
    public Optional<Category> getCategoryById(Long id){return categoryRepository.findById(id);}

    public List<Product> viewProductByCategory(Long categoryId) throws ProductException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            // Assuming you have a field in Category representing the products
            List<Product> products = category.get().getProducts();
            return products;
        } else {
            throw new ProductException("Category not found with id - " + categoryId);
        }
    }
}