package com.eren.ecommerce2.exception;

import com.eren.ecommerce2.model.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {

    public InvalidOrderStatusTransitionException(
            OrderStatus currentStatus,
            OrderStatus newStatus) {

        super(
                "Geçersiz sipariş durumu geçişi: "
                        + currentStatus
                        + " -> "
                        + newStatus);
    }
}