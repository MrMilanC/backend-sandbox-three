package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam("customerId") Long cId,
                                                 @RequestParam("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cService.addProductToCart(cId, productId), HttpStatus.OK);

    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable("cartId") Long cartId,
                                                      @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cService.removeProductFromCart(cartId, productId), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Cart> removeAllProduct(@PathVariable("cartId") Long cartId)
            throws CartException, UserException {
        return new ResponseEntity<Cart>(cService.removeAllProduct(cartId), HttpStatus.OK);
    }

    @PutMapping("/increase/{cartId}/{productId}")
    public ResponseEntity<Cart> increaseProductQuantity(@PathVariable("cartId") Long cartId,
                                                        @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cService.increaseProductQuantity(cartId, productId), HttpStatus.OK);
    }

    @PutMapping("/decrease/{cartId}/{productId}")
    public ResponseEntity<Cart> decreaseProductQuantity(@PathVariable("cartId") Long cartId,
                                                        @PathVariable("productId") Long productId) throws CartException, UserException, ProductException {
        return new ResponseEntity<Cart>(cService.decreaseProductQuantity(cartId, productId), HttpStatus.OK);
    }
}
