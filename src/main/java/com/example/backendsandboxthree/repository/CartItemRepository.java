package com.example.backendsandboxthree.repository;

import com.example.backendsandboxthree.model.CartItem;
import com.example.backendsandboxthree.model.Product;
import com.example.backendsandboxthree.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    ///List<CartItem> findByUser (User user);
    //CartItem findByUserAndProduct (User user, Product product);
}
