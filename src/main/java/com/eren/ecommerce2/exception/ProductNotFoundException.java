package com.eren.ecommerce2.exception;
// extend Runtime... -> bu class bir exception türüdür demeye yarıyor.
// artık ürün bulamazsa productnotfound diye özel hata ureteecek
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super(id + " idli ürün bulunamadı");
    }
}
