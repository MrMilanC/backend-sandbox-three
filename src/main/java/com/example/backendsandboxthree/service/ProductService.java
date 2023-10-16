package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.dto.NewProductDTO;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Category;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.repository.CategoryRepository;
import com.example.backendsandboxthree.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    public static String uploadDir = "C:/Users/milan/WebstormProjects/frontend-webshop-main/img/user-files";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> viewAllProduct() throws ProductException {
        List<Product> products = productRepository.findAll();
        if (products.size() > 0) {
            return products;
        } else {
            throw new ProductException("Products not found");
        }
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws ProductException, IOException {
        String imageUUID;
        if (!imageFile.isEmpty()) {
            imageUUID = imageFile.getOriginalFilename();
            Path fileNameandPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameandPath, imageFile.getBytes());
        } else {
            throw new ProductException("Image file is empty");
        }
        product.setImageName(imageUUID);

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) throws ProductException {
        Optional<Product> productUpdate = productRepository.findById(product.getProductId());
        if (productUpdate.isPresent()) {
            return productRepository.save(product);
        } else {
            throw new ProductException("Product not updated");
        }
    }

    public Product viewProduct(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ProductException("Product not found with product id - " + productId);
        }
    }

    public Product removeProduct(Long productId) throws ProductException {
        Product productRemoved = productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
        productRepository.delete(productRemoved);
        return productRemoved;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
