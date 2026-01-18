package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table("demo_product")
public class ProductCassandraEntity {
//    @PrimaryKey
    private long productId;
    private String productName;
    private double  productPrice;
}
