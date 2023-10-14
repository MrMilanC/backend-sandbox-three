package com.example.backendsandboxthree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
//@ToString
@Entity(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
//    @JsonManagedReference
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    //@JsonIgnore
    private Product product;

    @Column(name = "quantity_cart", nullable = false)
    private Integer quantityCart;

//    @Column(name="cart_item_price")
//    private double itemPrice;

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", cart=" + cart.getId() +
                ", product=" + product.getProductId() +
                ", quantityCart=" + quantityCart +
                '}';
    }

}
