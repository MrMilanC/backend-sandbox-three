package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.exception.ProductException;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.model.User;
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
    private CartRepository cRepo;
    @Autowired
    private UserRepository crRepo;
    @Autowired
    private ProductRepository pRepo;

    public Cart addProductToCart(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> opt = crRepo.findById(userId);
        if (opt.isEmpty())
            throw new UserException("Customer not found!");

        Optional<Product> itemOpt = pRepo.findById(productId);
        if (itemOpt.isEmpty())
            throw new ProductException("Product not found!");

        User user = opt.get();
        Cart cart = user.getCart();

        List<Product> itemList = cart.getProducts();
        boolean flag = true;
        for (int i = 0; i < itemList.size(); i++) {
            Product element = itemList.get(i);
            if (element.getProductId() == productId) {
                if (cart.getProduct_quantity() == null) {
                    cart.setProduct_quantity(1);

                } else {
                    cart.setProduct_quantity(cart.getProduct_quantity() + 1);
                }

                flag = false;
            }
        }
        if (flag) {
            cart.getProducts().add(itemOpt.get());
        }

        cRepo.save(cart);
        return cart;

    }

    public Cart removeProductFromCart(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> opt = crRepo.findById(userId);
        if (opt.isEmpty())
            throw new UserException("Customer not found!");

        Optional<Product> itemOpt = pRepo.findById(productId);
        if (itemOpt.isEmpty())
            throw new ProductException("Product not found!");
        User user = opt.get();
        Cart cart = user.getCart();
        List<Product> itemList = cart.getProducts();
        boolean flag = false;
        for (int i = 0; i < itemList.size(); i++) {
            Product element = itemList.get(i);
            if (element.getProductId() == productId) {
                itemList.remove(element);
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new CartException("Product not removed from cart");
        }
        cart.setProducts(itemList);
        cRepo.save(cart);
        return cart;

    }

    public Cart removeAllProduct(Long userId) throws CartException, UserException {
        Optional<User> opt = crRepo.findById(userId);
        if (opt.isEmpty())
            throw new UserException("Customer not found!");
        Cart c = opt.get().getCart();
        if (c == null) {
            throw new CartException("cart not found");
        }
        c.getProducts().clear();
        return cRepo.save(c);
    }

    public Cart increaseProductQuantity(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> opt = crRepo.findById(userId);
        if (opt.isEmpty())
            throw new UserException("Customer not found!");

        Optional<Product> itemOpt = pRepo.findById(productId);
        if (itemOpt.isEmpty())
            throw new ProductException("Product not found!");

        User user = opt.get();
        Cart cart = user.getCart();
        List<Product> itemList = cart.getProducts();
        boolean flag = true;
        for (int i = 0; i < itemList.size(); i++) {
            Product element = itemList.get(i);
            if (element.getProductId() == productId) {
                cart.setProduct_quantity(cart.getProduct_quantity() + 1);
                flag = false;
            }
        }
        if (flag) {
            cart.getProducts().add(itemOpt.get());
        }
        cRepo.save(cart);
        return cart;
    }

    public Cart decreaseProductQuantity(Long userId, Long productId)
            throws CartException, UserException, ProductException {
        Optional<User> opt = crRepo.findById(userId);
        if (opt.isEmpty())
            throw new UserException("Customer not found!");

        Optional<Product> itemOpt = pRepo.findById(productId);
        if (itemOpt.isEmpty())
            throw new ProductException("Product not found!");

        User user = opt.get();
        Cart cart = user.getCart();
        List<Product> itemList = cart.getProducts();
        boolean flag = true;
        if (itemList.size() > 0) {
            for (int i = 0; i < itemList.size(); i++) {
                Product element = itemList.get(i);
                if (element.getProductId() == productId) {
                    cart.setProduct_quantity(cart.getProduct_quantity() + 1);
                    flag = false;
                }
            }
        }

        if (flag) {
            cart.getProducts().add(itemOpt.get());
        }
        cRepo.save(cart);
        return cart;
    }

}

