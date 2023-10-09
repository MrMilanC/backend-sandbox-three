package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.*;
import com.example.backendsandboxthree.repository.CartRepository;
import com.example.backendsandboxthree.repository.ProductRepository;
import com.example.backendsandboxthree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart saveCart (String username) throws UserException {

//        Optional<User> userOpt = userRepository.findByUsername(username.getUserName());
        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
//        if (userOpt.isEmpty()) {
//            throw new UserException("User not found!");
//        }

        Long userId = userOpt.getId();

        Cart shoppingCart = new Cart();
        shoppingCart.setUserId(userId);

        return cartRepository.save(shoppingCart);
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addProductToCart(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException("User not found!");
        }

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User user = userOpt.get();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        boolean productFoundInCart = false;

        // Check if the product is already in the cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productOpt.get())) {
                cartItem.setQuantityCart(cartItem.getQuantityCart() + 1);
                productFoundInCart = true;
                break;
            }
        }

        if (!productFoundInCart) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(productOpt.get());
            newCartItem.setQuantityCart(1);
            cartItems.add(newCartItem);
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeProductFromCart(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException("User not found!");
        }

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User user = userOpt.get();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        boolean productFoundInCart = false;
        CartItem cartItemToRemove = null;

        // Find the cart item to remove
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productOpt.get())) {
                if (cartItem.getQuantityCart() > 1) {
                    cartItem.setQuantityCart(cartItem.getQuantityCart() - 1);
                } else {
                    cartItemToRemove = cartItem;
                }
                productFoundInCart = true;
                break;
            }
        }

        if (productFoundInCart) {
            cartItems.remove(cartItemToRemove);
        } else {
            throw new CartException("Product not found in the cart");
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeAllProduct(Long userId) throws CartException, UserException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException("User not found!");
        }

        User user = userOpt.get();
        Cart cart = user.getCart();
        cart.setCartItems(null); // Clear all cart items

        cartRepository.save(cart);
        return cart;
    }

    public Cart increaseProductQuantity(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException("User not found!");
        }

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User user = userOpt.get();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        boolean productFoundInCart = false;

        // Find the cart item to increase quantity
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productOpt.get())) {
                cartItem.setQuantityCart(cartItem.getQuantityCart() + 1);
                productFoundInCart = true;
                break;
            }
        }

        if (!productFoundInCart) {
            throw new CartException("Product not found in the cart");
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

    public Cart decreaseProductQuantity(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException("User not found!");
        }

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User user = userOpt.get();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        boolean productFoundInCart = false;

        // Find the cart item to decrease quantity
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productOpt.get())) {
                if (cartItem.getQuantityCart() > 1) {
                    cartItem.setQuantityCart(cartItem.getQuantityCart() - 1);
                } else {
                    cartItems.remove(cartItem);
                }
                productFoundInCart = true;
                break;
            }
        }

        if (!productFoundInCart) {
            throw new CartException("Product not found in the cart");
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

}

