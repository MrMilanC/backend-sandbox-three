package com.example.backendsandboxthree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    //@OneToMany(mappedBy = "category_id")
    private Long categoryId;

    //@OneToMany(mappedBy = "category_name")
    private String categoryName;

//    @JsonIgnore
//    @OneToMany(mappedBy = "category")
//    private List<Product> productList;
}
