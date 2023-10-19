package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.service.CartService;
import com.example.backendsandboxthree.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCart() throws UserException, CartException {
        String userName = "testUser";
        Cart expectedCart = new Cart(); // Create a sample Cart

        when(cartService.saveCart(userName)).thenReturn(expectedCart);

        ResponseEntity<Cart> response = cartController.create(userName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testViewCartById() throws CartException {
        String userName = "testUser";
        Cart expectedCart = new Cart(); // Create a sample Cart

        when(cartService.viewCart(userName)).thenReturn(expectedCart);

        ResponseEntity<Cart> response = cartController.viewCartById(userName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testAddProductToCart() throws CartException, UserException, ProductException {
        String userName = "testUser";
        Long productId = 1L;
        Cart expectedCart = new Cart(); // Create a sample Cart

        when(cartService.addProductToCart(userName, productId)).thenReturn(expectedCart);

        ResponseEntity<Cart> response = cartController.addProductToCart(userName, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testRemoveProductFromCart() throws CartException, UserException, ProductException {
        String userName = "testUser";
        Long productId = 1L;
        Cart expectedCart = new Cart(); // Create a sample Cart

        when(cartService.removeProductFromCart(userName, productId)).thenReturn(expectedCart);

        ResponseEntity<Cart> response = cartController.removeProductFromCart(userName, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testRemoveAllProduct() throws CartException, UserException, ProductException {
        String userName = "testUser";
        Cart expectedCart = new Cart(); // Create a sample Cart

        when(cartService.removeAllProduct(userName)).thenReturn(expectedCart);

        ResponseEntity<Cart> response = cartController.removeAllProduct(userName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testViewCartItemQuantity() throws CartException, UserException, ProductException {
        String userName = "testUser";
        Long productId = 1L;
        int expectedQuantity = 5;

        when(cartService.viewCartItemQuantity(userName, productId)).thenReturn(expectedQuantity);

        ResponseEntity<Integer> response = cartController.viewCartItemQuantity(userName, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedQuantity, response.getBody());
    }
}
