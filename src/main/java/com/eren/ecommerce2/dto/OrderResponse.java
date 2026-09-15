// OrderResponse.java
package com.eren.ecommerce2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eren.ecommerce2.model.OrderStatus;

public record OrderResponse(
        Long id,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items,
        BigDecimal totalPrice) {
}