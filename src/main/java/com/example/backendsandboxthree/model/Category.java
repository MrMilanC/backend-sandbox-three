package com.example.backendsandboxthree.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    private String categoryName;

    public void setId(Long categoryId) {
    }

    public void setProductList(List<Product> products) {
    }
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public List<Product> getProductList() {
        return products;
    }
}
