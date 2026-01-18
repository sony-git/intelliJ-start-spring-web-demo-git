package com.example.demo.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
  private long productId;
  private String productName;
  private double productPrice;
  private String productDescription;
  private String productBrand;
  private String productImageUrl;
}
