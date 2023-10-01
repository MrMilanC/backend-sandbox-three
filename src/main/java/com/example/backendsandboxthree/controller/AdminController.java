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

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img";
    //public static String uploadDir = "http://localhost:63343/frontend-webshop-main/img/user-files";

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

//    @GetMapping("/products/view")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<List<Product>> viewAllProduct() throws ProductException {
//        return new ResponseEntity<List<Product>>(productService.viewAllProduct(), HttpStatus.OK);
//    }

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
        productService.addProduct(product);

        Product newProduct = productService.addProduct(product);

        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

//    @GetMapping("/products/update/{productId}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id) throws ProductException, IOException {
//        Product product = productService.getProductById(id);
//        NewProductDTO newProductDTO = new NewProductDTO();
//        newProductDTO.setProductId(product.get().getProductId());
//        newProductDTO.setProductName(product.get().getProductName());
//        newProductDTO.setPrice(product.get().getPrice());
//        newProductDTO.setDescription(product.get().getDescription());
//        newProductDTO.setQuantity(product.get().getQuantity());
//        newProductDTO.setImageName(product.get().getImageName());
//        newProductDTO.setCategoryId(product.get().getCategory().getCategoryId());
//        Product newProduct = productService.updateProduct(product);
//        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
//    }

//    @PutMapping
//    public ResponseEntity<NewProductDTO> update(@Validated @RequestBody NewProductDTO newProductDTO) {
//        try {
//            return ResponseEntity.ok(productService.update(newProductDTO).convertToDto());
//        } catch (ProductException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody NewProductDTO newProductDTO) {
        try {
            // Retrieve the existing product by its ID
            Product existingProduct = productService.getProductById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with ID: " + productId));

            // Update the existing product with data from the DTO
            existingProduct.setProductId(newProductDTO.getProductId());
            existingProduct.setProductName(newProductDTO.getProductName());
            existingProduct.setPrice(newProductDTO.getPrice());
            existingProduct.setDescription(newProductDTO.getDescription());
            existingProduct.setQuantity(newProductDTO.getQuantity());
            existingProduct.setImageName(newProductDTO.getImageName());
            existingProduct.setCategory(categoryService.getCategoryById(newProductDTO.getCategoryId()).orElse(null));

            // Save the updated product
            Product updatedProduct = productService.updateProduct(existingProduct);//productService.save.(product)

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductException e) {
            // Handle the case where the product with the given ID is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions here
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/remove/{productId}")  //DONE
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

    @DeleteMapping("/users/remove/{userId}")  //DONE
    public ResponseEntity<User> removeUserById(@PathVariable("userId") Long userId)
            throws UserException {
        return new ResponseEntity<User>(userService.removeUser(userId), HttpStatus.OK);
    }
}

