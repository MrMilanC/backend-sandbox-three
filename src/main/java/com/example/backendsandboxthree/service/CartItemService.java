package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.CartException;
import com.example.backendsandboxthree.model.Cart;
import com.example.backendsandboxthree.model.User;
import com.example.backendsandboxthree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

//    @Autowired
//    private UserRepository userRepository;
//
//    public Integer viewCartItemQuantity(String username) throws CartException {
//
//        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
//        Long userId = userOpt.getId();
//        Cart cartOpt = cartRepository.findByUserId(userId);
//
//        return cartOpt;
//    }


}
