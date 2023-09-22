package com.example.backendsandboxthree.dto;

import com.example.backendsandboxthree.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class NewProductDTO {

    private Long productId;
    private String productName;
    private double price;
    private String description;
    private double quantity;
    private String imageName;
    private Long categoryId;

}

