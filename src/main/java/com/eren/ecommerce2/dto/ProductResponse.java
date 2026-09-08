package com.eren.ecommerce2.dto;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    long categoryId,
    String categoryName
) {
    
}
