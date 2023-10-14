package com.example.backendsandboxthree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
//@ToString
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

    @OneToOne(mappedBy = "cart", cascade = CascadeType.REMOVE)//@OneToOne//(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

}
