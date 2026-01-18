package com.example.demo.model.pojo;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private long productId;
    private String productName;
    private double  productPrice;
    private String productDescription;
    private String productBrand;
    private String productImageUrl;
}
