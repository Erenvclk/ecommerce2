package com.eren.ecommerce2.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super(orderId + " id'li sipariş bulunamadı");
    }
}