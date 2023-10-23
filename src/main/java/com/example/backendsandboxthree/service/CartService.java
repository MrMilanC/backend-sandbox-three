package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.model.CartItem;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.model.User;
import com.example.backendsandboxthree.repository.CartItemRepository;
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
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart saveCart(String username) throws CartException {

        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);

        Long userId = userOpt.getId();
        Cart cartOpt = cartRepository.findByUserId(userId);
        if (!(cartOpt == null)) {
            return cartOpt;
        }

        Cart shoppingCart = new Cart();
        User user = userRepository.findById(userId).get();
        user.setCart(shoppingCart);
        shoppingCart.setUser(user);

        return cartRepository.save(shoppingCart);
    }

    public Cart viewCart(String username) throws CartException {

        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        Long userId = userOpt.getId();
        Cart cartOpt = cartRepository.findByUserId(userId);

        return cartOpt;
    }

    public Cart addProductToCart(String userName, Long productId) throws CartException, UserException, ProductException {

        User userOpt = userRepository.findByUsername(userName).orElseThrow(RuntimeException::new);
        Long cartId = userOpt.getCart().getId();

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User user = userRepository.findById(cartId).get();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        boolean productFoundInCart = false;

        // Check if the product is already in the cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productOpt.get())) {
                cartItem.setQuantityCart(cartItem.getQuantityCart() + 1);
                productFoundInCart = true;
                cartItemRepository.save(cartItem);
                break;
            }
        }

        if (!productFoundInCart) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(productOpt.get());
            newCartItem.setQuantityCart(1);
            cartItems.add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeProductFromCart(String userName, Long productId) throws CartException, UserException, ProductException {

        User userOpt = userRepository.findByUsername(userName).orElseThrow(RuntimeException::new);

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        Long userId = userOpt.getId();
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cart.getCartItems();
        //cartItems.stream().forEach(System.out::println);

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

        if (cartItemToRemove != null) {
            if (productFoundInCart) {
                cartItems.remove(cartItemToRemove);
                cartItemRepository.delete(cartItemToRemove);
            } else {
                throw new CartException("Product not found in the cart");
            }
        }

        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeAllProduct(String userName) throws CartException, UserException {

        User userOpt = userRepository.findByUsername(userName).orElseThrow(RuntimeException::new);
        Cart cart = userOpt.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        // Delete all cart items from the database
        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }

        // Clear the cart items list
        cartItems.clear();
        cart.setCartItems(null);

        cartRepository.save(cart);
        return cart;
    }

    public Integer viewCartItemQuantity(String username, Long productId) throws CartException, ProductException {

        Integer quantity = 0;

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("Product not found!");
        }

        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        Long userId = userOpt.getId();
        Cart cartOpt = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartOpt.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId().equals(productOpt.get().getProductId())) {
                quantity += cartItem.getQuantityCart();
            }
        }
        return quantity;
    }
}

