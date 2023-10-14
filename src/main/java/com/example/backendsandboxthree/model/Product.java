package com.example.backendsandboxthree.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
//@Embeddable
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private double price;
    private String description;
    private double quantity;
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToOne(mappedBy = "item")
//    CartItem cartItem;

//    @ManyToOne //(cascade = CascadeType.ALL)
//    //@JoinColumn (name = "category_id", referencedColumnName = "category_id")
//    private Category category;

//    @OneToMany(mappedBy = "cart")
//    private CartItem cartItem;

//    @OneToOne(mappedBy = "item")
//    CartItem cartItem;

//    @ManyToOne //(cascade = CascadeType.ALL)
//    //@JoinColumn (name = "category_id", referencedColumnName = "category_id")
//    private Category category;
}
