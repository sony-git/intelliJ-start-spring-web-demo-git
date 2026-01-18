package com.example.demo.common;

import com.example.demo.model.entity.ProductCosmosEntity;
import com.example.demo.model.entity.ProductEntity;
import com.example.demo.model.pojo.ProductRequestDTO;
import com.example.demo.model.pojo.ProductResponseDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductAdapterHelper {
    public static ProductEntity buildProductEntity(ProductRequestDTO productRequestDTO) {
        return ProductEntity.builder()
                .productId(productRequestDTO.getProductId())
                .productName(productRequestDTO.getProductName())
                .productBrand(productRequestDTO.getProductBrand())
                .productDescription(productRequestDTO.getProductDescription())
                .productImageUrl(productRequestDTO.getProductImageUrl())
                .productPrice(productRequestDTO.getProductPrice())
                .build();
    }

    public static List<ProductResponseDTO> buildProductResponse(List<?> productEntities) {
        if (productEntities == null || productEntities.isEmpty()) {
            return List.of();
        }
        Object firstEntity = productEntities.get(0);
        if (firstEntity instanceof ProductEntity) {
            return productEntities.stream()
                    .map(entity -> (ProductEntity) entity)
                    .map(ProductAdapterHelper::buildProductResponse)
                    .toList();
        } else if (firstEntity instanceof ProductCosmosEntity) {
            return productEntities.stream()
                    .map(entity -> (ProductCosmosEntity) entity)
                    .map(ProductAdapterHelper::buildProductResponse)
                    .toList();
        } else {
            log.error("Unknown entity type: " + firstEntity.getClass().getName());
            return List.of();
        }
    }

    private static ProductResponseDTO buildProductResponse(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        return ProductResponseDTO.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .productBrand(productEntity.getProductBrand())
                .productDescription(productEntity.getProductDescription())
                .productImageUrl(productEntity.getProductImageUrl())
                .productPrice(productEntity.getProductPrice())
                .build();
    }


    private static ProductResponseDTO buildProductResponse(ProductCosmosEntity productCosmosEntity) {
        if (productCosmosEntity == null) {
            return null;
        }
        return ProductResponseDTO.builder()
                .productId(productCosmosEntity.getProductId())
                .productName(productCosmosEntity.getProductName())
                .productPrice(productCosmosEntity.getProductPrice())
                .build();
    }
}
