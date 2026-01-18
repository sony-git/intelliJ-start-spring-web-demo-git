package com.example.demo.repository;

import com.example.demo.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface H2JpaProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("Select p from ProductEntity p where p.productName like %:keyword%")
    ProductEntity queryByProductName(String keyword);
}