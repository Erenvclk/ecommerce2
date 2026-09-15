package com.eren.ecommerce2.mapper;

import java.util.List;

import com.eren.ecommerce2.dto.OrderItemResponse;
import com.eren.ecommerce2.dto.OrderResponse;
import com.eren.ecommerce2.model.Order;
import com.eren.ecommerce2.model.OrderItem;

public class OrderMapper {

    public static OrderResponse toResponse(
            Order order,
            List<OrderItem> orderItems) {

        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(OrderItemMapper::toResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                itemResponses,
                order.getTotalPrice());
    }
}