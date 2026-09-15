package com.eren.ecommerce2.exception;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(Long cartId) {
        super(cartId + " id'li sepet boş");
    }
}