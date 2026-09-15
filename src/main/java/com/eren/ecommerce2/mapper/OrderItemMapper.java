package com.eren.ecommerce2.mapper;

import com.eren.ecommerce2.dto.OrderItemResponse;
import com.eren.ecommerce2.model.OrderItem;

public class OrderItemMapper {

    public static OrderItemResponse toResponse(OrderItem orderItem) {

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getLineTotal());
    }
}