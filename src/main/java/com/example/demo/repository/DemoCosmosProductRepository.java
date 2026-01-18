package com.example.demo.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.demo.model.entity.ProductCosmosEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoCosmosProductRepository extends CosmosRepository<ProductCosmosEntity, Long> {

}
