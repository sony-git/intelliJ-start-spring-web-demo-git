package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductEntity {
    @Id
    private long productId;
    private String productName;
    private double  productPrice;
    private String productDescription;
    private String productBrand;
    private String productImageUrl;
}
