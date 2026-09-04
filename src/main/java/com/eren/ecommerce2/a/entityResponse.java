package com.eren.ecommerce2.a;

public record entityResponse(
    Long id,
    String name,
    String description,
    double price,
    int stock) {
    
}
