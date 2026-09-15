package com.eren.ecommerce2.exception;

public class StockExceededException extends RuntimeException{
    
    public StockExceededException(Integer stock){
            super("stok miktarı aşıldı. Mevcut stok : " + stock);
    }
}
