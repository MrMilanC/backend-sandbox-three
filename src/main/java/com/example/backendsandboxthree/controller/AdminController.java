package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.NewProductDTO;
import com.example.backendsandboxthree.dto.NewUserDTO;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Category;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.model.User;
import com.example.backendsandboxthree.repository.UserRepository;
import com.example.backendsandboxthree.service.CategoryService;
import com.example.backendsandboxthree.service.ProductService;
import com.example.backendsandboxthree.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:63343")
public class AdminController {

    /////////////////////Product Section
    public static String uploadDir = "C:/Users/milan/WebstormProjects/frontend-webshop-main/img/user-files";
    //public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img";

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/products/add")
    public ResponseEntity<Product> addProduct(@RequestParam("productName") String productName,
                                                    @RequestParam("productPrice") double productPrice,
                                                    @RequestParam("productDescription") String productDescription,
                                                    @RequestParam("productQuantity") double productQuantity,
                                                    @RequestParam("categoryId") Category categoryId,
                                                    @RequestParam("productImage") MultipartFile imageFile,
                                                    @RequestParam("imageName") String imageName) throws ProductException, IOException {
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(productPrice);
        product.setDescription(productDescription);
        product.setQuantity(productQuantity);
        product.setCategory(categoryId);
        String imageUUID;
        if(!imageFile.isEmpty()){
            imageUUID = imageFile.getOriginalFilename();
            Path fileNameandPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameandPath, imageFile.getBytes());
        } else {
            imageUUID = imageName;
        }
        product.setImageName(imageUUID);
        //productService.addProduct(product);

        Product newProduct = productService.addProduct(product);

        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestParam("productName") String productName,
                                                 @RequestParam("productPrice") double productPrice,
                                                 @RequestParam("productDescription") String productDescription,
                                                 @RequestParam("productQuantity") double productQuantity,
                                                 @RequestParam("categoryId") Long categoryId,
                                                 //@RequestParam(name = "categoryId", required = false) Category categoryId,
                                                 @RequestParam("productImage") MultipartFile imageFile,
                                                 @RequestParam("imageName") String imageName) throws ProductException, IOException {

            // Retrieve the existing product by its ID
            Product existingProduct = productService.getProductById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with ID: " + productId));

            // Update the existing product with data from the DTO

        existingProduct.setProductName(productName);
        existingProduct.setPrice(productPrice);
        existingProduct.setDescription(productDescription);
        existingProduct.setQuantity(productQuantity);
 //       existingProduct.setCategory(categoryId);

//        newProductDTO.setCategoryId(product.get().getCategory().getCategoryId());
//
//        existingProduct.setImageName(newProductDTO.getImageName());

        existingProduct.setCategory(categoryService.getCategoryById(categoryId).orElse(null));

        String imageUUID;
        if(!imageFile.isEmpty()){
            imageUUID = imageFile.getOriginalFilename();
            Path fileNameandPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameandPath, imageFile.getBytes());
        } else {
            imageUUID = imageName;
        }
        existingProduct.setImageName(imageUUID);

        Product updatedProduct = productService.updateProduct(existingProduct);

        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);

    }

        @DeleteMapping("/products/remove/{productId}")
    public ResponseEntity<Product> removeProductById(@PathVariable("productId") Long productId)
            throws ProductException {
        return new ResponseEntity<Product>(productService.removeProduct(productId), HttpStatus.OK);
    }


    //////////////////////User Section

    @Autowired //erstellt automatisch Repository
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/view")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@PreAuthorize(hasRole('ADMIN')")
    public ResponseEntity<List<User>> viewAllUser() throws UserException {
        return new ResponseEntity<List<User>>(userService.viewAllUser(), HttpStatus.OK);
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserDTO newUserDTO) throws UserException {

        // checking for username exists in a database
        if(userService.doesUsernameExist(newUserDTO.getUsername())){
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setUsername(newUserDTO.getUsername());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user.setRole(newUserDTO.getRole());

        userService.addUser(user);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/users/remove/{userId}")
    public ResponseEntity<User> removeUserById(@PathVariable("userId") Long userId)
            throws UserException {
        return new ResponseEntity<User>(userService.removeUser(userId), HttpStatus.OK);
    }
}

