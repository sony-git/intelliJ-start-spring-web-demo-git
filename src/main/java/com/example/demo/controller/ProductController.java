package com.example.demo.controller;

import com.example.demo.model.pojo.*;
import com.example.demo.model.entity.ProductEntity;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class ProductController {

  private final ProductService productService;

  @Value("${app.version}")
  private String appVersion;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("product/create")
  public ResponseEntity<?> createProduct(
          @Valid @RequestBody ProductRequestDTO productRequestDTO, HttpServletRequest request) {
    try {
      ProductEntity createdProductEntity = productService.createProduct(productRequestDTO);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.CREATED.value());
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ApiResponseDTO<>(meta, createdProductEntity));
    } catch (Exception e) {
      log.error("Error creating product: {}", e.getMessage(), e);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.INTERNAL_SERVER_ERROR.value());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new ApiResponseDTO<>(
                  meta,
                  new ErrorResponseDTO(
                      "INTERNAL_ERROR",
                      "An unexpected error occurred while creating the product.")));
    }
  }

  @GetMapping("product/list/all")
  public ResponseEntity<?> listAllProducts(@RequestParam(required = false) String source,
                                           @Valid HttpServletRequest request) {
    try {
      List<ProductResponseDTO> productResponsDTOs = productService.getAllProducts(source);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.OK.value());
      return ResponseEntity.ok(new ApiResponseDTO<>(meta, productResponsDTOs));
    } catch (Exception e) {
      log.error("Error fetching all products: {}", e.getMessage(), e);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.INTERNAL_SERVER_ERROR.value());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new ApiResponseDTO<>(
                  meta,
                  new ErrorResponseDTO(
                      "INTERNAL_ERROR",
                      "An unexpected error occurred while fetching all products.")));
    }
  }

  @GetMapping("product/list")
  public ResponseEntity<?> listProduct(@RequestParam Long productId,
                                       @RequestParam(required = false) String source,
                                       HttpServletRequest request) {
    try {
      List<ProductResponseDTO> productResponsDTOs = productService.getProductById(productId,source);
      if (productResponsDTOs.isEmpty()) {
        log.warn("Product not found with id: {}", productId);
        MetaDataDTO meta = buildMetaData(request, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                new ApiResponseDTO<>(
                    meta,
                    new ErrorResponseDTO("NOT_FOUND", "No product found with id: " + productId)));
      }
      MetaDataDTO meta = buildMetaData(request, HttpStatus.OK.value());
      return ResponseEntity.ok(new ApiResponseDTO<>(meta, productResponsDTOs));
    } catch (Exception e) {
      log.error("Error fetching product with id {}: {}", productId, e.getMessage(), e);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.INTERNAL_SERVER_ERROR.value());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new ApiResponseDTO<>(
                  meta,
                  new ErrorResponseDTO(
                      "INTERNAL_ERROR",
                      "An unexpected error occurred while fetching the product.")));
    }
  }

  @GetMapping("product/list/{productId}")
  public ResponseEntity<?> listProductPath(
      @PathVariable Long productId, HttpServletRequest request) {
    try {
      List<ProductResponseDTO> productResponsDTOs = productService.getProductById(productId,"h2");
      if (productResponsDTOs.isEmpty()) {
        log.warn("Product not found with id: {}", productId);
        MetaDataDTO meta = buildMetaData(request, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                new ApiResponseDTO<>(
                    meta,
                    new ErrorResponseDTO("NOT_FOUND", "No product found with id: " + productId)));
      }
      MetaDataDTO meta = buildMetaData(request, HttpStatus.OK.value());
      return ResponseEntity.ok(new ApiResponseDTO<>(meta, productResponsDTOs));
    } catch (Exception e) {
      log.error("Error fetching product with id {}: {}", productId, e.getMessage(), e);
      MetaDataDTO meta = buildMetaData(request, HttpStatus.INTERNAL_SERVER_ERROR.value());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new ApiResponseDTO<>(
                  meta,
                  new ErrorResponseDTO(
                      "INTERNAL_ERROR",
                      "An unexpected error occurred while fetching the product.")));
    }
  }

  @DeleteMapping("product/delete")
  public ResponseEntity<?> deleteProduct(
      @RequestParam(required = false) Long productId,
      @RequestParam(required = false) String productName,
      HttpServletRequest request) {
    if (productId == null && (productName == null || productName.isEmpty())) {
      MetaDataDTO meta = buildMetaData(request, HttpStatus.BAD_REQUEST.value());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(
              new ApiResponseDTO<>(
                  meta,
                  new ErrorResponseDTO("INVALID_REQUEST", "Provide either productId or productName")));
    }
    boolean deleted = productService.deleteProduct(productId, productName);
    if (deleted) {
      MetaDataDTO meta = buildMetaData(request, HttpStatus.OK.value());
      return ResponseEntity.ok(new ApiResponseDTO<>(meta, "Product deleted successfully"));
    } else {
      MetaDataDTO meta = buildMetaData(request, HttpStatus.NOT_FOUND.value());
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(
              new ApiResponseDTO<>(
                  meta, new ErrorResponseDTO("NOT_FOUND", "No product found to delete")));
    }
  }

  private MetaDataDTO buildMetaData(HttpServletRequest request, int status) {
    return new MetaDataDTO(
        request.getRequestURI(),
        request.getMethod(),
        status,
        appVersion,
        request.getParameterMap()
    );
  }
}
