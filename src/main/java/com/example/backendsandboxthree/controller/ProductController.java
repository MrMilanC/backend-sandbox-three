package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.service.CategoryService;
import com.example.backendsandboxthree.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:63343", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/view")
    public ResponseEntity<List<Product>> viewAllProduct() throws ProductException {
        return new ResponseEntity<List<Product>>(productService.viewAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/view/{productId}")
    public ResponseEntity<Product> viewProductById(@PathVariable("productId") Long productId) throws ProductException {
        return new ResponseEntity<Product>(productService.viewProduct(productId), HttpStatus.OK);
    }

    @GetMapping("view/category/{categoryId}")
    public ResponseEntity<List<Product>> viewProductByCategoryId(@PathVariable("categoryId") Long categoryId)
            throws ProductException {
            System.out.println("gedruckt");
        return new ResponseEntity<List<Product>>(categoryService.viewProductByCategory(categoryId), HttpStatus.OK);
    }
}
