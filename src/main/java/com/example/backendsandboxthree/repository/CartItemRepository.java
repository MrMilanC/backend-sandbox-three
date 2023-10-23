package com.example.backendsandboxthree.repository;

import com.example.backendsandboxthree.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
