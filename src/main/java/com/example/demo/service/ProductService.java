package com.example.demo.service;

import com.example.demo.common.ProductAdapterHelper;
import com.example.demo.model.entity.ProductCassandraEntity;
import com.example.demo.model.entity.ProductCosmosEntity;
import com.example.demo.model.entity.ProductEntity;
import com.example.demo.model.pojo.ProductRequestDTO;
import com.example.demo.model.pojo.ProductResponseDTO;
import com.example.demo.repository.DemoCosmosProductRepository;
import com.example.demo.repository.H2JpaProductRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

  H2JpaProductRepository h2JpaProductRepository;

  //    DemoCassandraProductRepository demoCassandraProductRepository;

  DemoCosmosProductRepository demoCosmosProductRepository;

  ProductService(
      H2JpaProductRepository h2JpaProductRepository,
      DemoCosmosProductRepository demoCosmosProductRepository) {
    this.h2JpaProductRepository = h2JpaProductRepository;
    //        this.demoCassandraProductRepository = demoCassandraProductRepository;
    this.demoCosmosProductRepository = demoCosmosProductRepository;
    initialiseProductList();
  }

  /** Initializes the product list with some default products. */
  private void initialiseProductList() {
    List<ProductEntity> productEntityList = new ArrayList<>();
    productEntityList.add(new ProductEntity(110120001, "iphone 16 h2", 120000, null, null, null));
    productEntityList.add(new ProductEntity(110120002, "samsung s25 h2", 90000, null, null, null));
    productEntityList.add(new ProductEntity(110120003, "onePlus 13 h2", 70000, null, null, null));
    h2JpaProductRepository.saveAll(productEntityList);

    List<ProductCosmosEntity> productCosmosContainersList = new ArrayList<>();
    productCosmosContainersList.add(new ProductCosmosEntity(110120001, "iphone 16 cosmos", 120000));
    productCosmosContainersList.add(
        new ProductCosmosEntity(110120002, "samsung s25 cosmos", 90000));
    productCosmosContainersList.add(new ProductCosmosEntity(110120003, "onePlus 13 cosmos", 70000));
    demoCosmosProductRepository.saveAll(productCosmosContainersList);

    List<ProductCassandraEntity> productCassandraEntityList = new ArrayList<>();
    productCassandraEntityList.add(
        new ProductCassandraEntity(110120001, "iphone 16 cassandra", 120000));
    productCassandraEntityList.add(
        new ProductCassandraEntity(110120002, "samsung s25 cassandra", 90000));
    productCassandraEntityList.add(
        new ProductCassandraEntity(110120003, "onePlus 13 cassandra", 70000));
    //        demoCassandraProductRepository.saveAll(productCassandraEntityList);

    log.info("Product list initialized with default products.");
  }

  /**
   * Retrieves all products.
   *
   * @return
   */
  public List<ProductResponseDTO> getAllProducts(String source) {
    if ("cosmos".equalsIgnoreCase(source)) {
      Iterable<ProductCosmosEntity> cosmosIterable = demoCosmosProductRepository.findAll();
      List<ProductCosmosEntity> cosmosList = StreamSupport
          .stream(cosmosIterable.spliterator(), false)
          .collect(Collectors.toList());
      return ProductAdapterHelper.buildProductResponse(cosmosList);
    } else {
      return ProductAdapterHelper.buildProductResponse(h2JpaProductRepository.findAll());
    }
  }

  /**
   * Retrieves a product by its ID.
   *
   * @param productId
   * @return
   */
  public List<ProductResponseDTO> getProductById(long productId, String source) {
    if ("cosmos".equalsIgnoreCase(source)) {
      return ProductAdapterHelper.buildProductResponse(
          Collections.singletonList(demoCosmosProductRepository.findById(productId).orElse(null)));
    } else {
      return ProductAdapterHelper.buildProductResponse(
          Collections.singletonList(h2JpaProductRepository.findById(productId).orElse(null)));
    }
  }

  /**
   * Deletes a product by its ID or name.
   *
   * @param productId
   * @param productName
   * @return
   */
  public boolean deleteProduct(Long productId, String productName) {
    if (productId != null) {
      h2JpaProductRepository.deleteById(productId);
      return true;
    } else if (productName != null && !productName.isEmpty()) {
      ProductEntity productEntity =
          h2JpaProductRepository.findAll().stream()
              .filter(p -> p.getProductName().equalsIgnoreCase(productName))
              .findFirst()
              .orElse(null);
      if (productEntity != null) {
        h2JpaProductRepository.delete(productEntity);
        return true;
      }
    }
    return false;
  }

  public ProductEntity createProduct(ProductRequestDTO productRequestDTO) {
    ProductEntity productEntity = ProductAdapterHelper.buildProductEntity(productRequestDTO);
    if (productEntity == null
        || productEntity.getProductName() == null
        || productEntity.getProductName().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    return h2JpaProductRepository.save(productEntity);
  }

  
}
