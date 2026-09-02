package com.eren.ecommerce2.dto;
import java.math.BigDecimal;

public record ProductRequest(
    
    String name,
    String description,
    BigDecimal price,
    Integer stock
) {
    
}
