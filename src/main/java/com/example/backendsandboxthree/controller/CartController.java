package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.service.CartService;
import com.example.backendsandboxthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:63343", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @ResponseStatus(code = CREATED)
    @PostMapping("/create/{userName}")
    public ResponseEntity<Cart> create(@PathVariable String userName) throws UserException, CartException {
        return  new ResponseEntity<Cart>(cartService.saveCart(userName), HttpStatus.OK);
    }

    @GetMapping("/view/{userName}")
    public ResponseEntity<Cart> viewCartById(@PathVariable("userName") String userName) throws CartException {
        return new ResponseEntity<Cart>(cartService.viewCart(userName), HttpStatus.OK);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addProductToCart(@RequestParam("userName") String userName,
                                                 @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cartService.addProductToCart(userName, productId), HttpStatus.OK);

    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam("userName") String userName,
                                                      @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cartService.removeProductFromCart(userName, productId), HttpStatus.OK);
    }

//    @DeleteMapping("/remove/{cartId}")
//    public ResponseEntity<Cart> removeAllProduct(@PathVariable("cartId") Long cartId)
//            throws CartException, UserException {
//        return new ResponseEntity<Cart>(cartService.removeAllProduct(cartId), HttpStatus.OK);
//    }

    @PutMapping("/increase/{cartId}/{productId}")
    public ResponseEntity<Cart> increaseProductQuantity(@PathVariable("cartId") Long cartId,
                                                        @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cartService.increaseProductQuantity(cartId, productId), HttpStatus.OK);
    }

    @PutMapping("/decrease/{cartId}/{productId}")
    public ResponseEntity<Cart> decreaseProductQuantity(@PathVariable("cartId") Long cartId,
                                                        @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cartService.decreaseProductQuantity(cartId, productId), HttpStatus.OK);
    }
}
