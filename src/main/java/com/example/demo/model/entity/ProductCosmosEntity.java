package com.example.demo.model.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Container(containerName = "idDemo")
public class ProductCosmosEntity {
    @Id
    private long productId;
    private String productName;
    private double  productPrice;
}
