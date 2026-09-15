package com.eren.ecommerce2.dto;

import com.eren.ecommerce2.model.OrderStatus;

public record UpdateOrderStatusRequest(
        OrderStatus status) {
}