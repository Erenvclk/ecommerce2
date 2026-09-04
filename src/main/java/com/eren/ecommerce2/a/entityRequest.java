package com.eren.ecommerce2.a;
import java.math.BigDecimal;
public record entityRequest(
    String name,
     String description,
      BigDecimal price) {
}

