package com.example.backendsandboxthree.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //private Integer cartItemQuantity;

//    @Column(name="cart_total_number_of_items")
//    private long totalNumberOfItems;
//    @Column(name="cart_total_price")
//    private double totalPrice;

    //@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    //@OneToOne(mappedBy = "cart")//@OneToOne//(cascade = CascadeType.ALL)
    private Long userId;
}
