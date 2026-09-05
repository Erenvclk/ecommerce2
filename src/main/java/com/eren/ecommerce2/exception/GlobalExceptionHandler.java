package com.eren.ecommerce2.exception;
// Product exceptionu yazdık ama http yanıtı olarak 
//gosterilmesi için bu sayfaya ihtiyac var
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // bu class global hata ayıklayıcısıdır

public class GlobalExceptionHandler {
    
    @ExceptionHandler(ProductNotFoundException.class) // sadece bu hata türünü yakala
    public ResponseEntity<String> handleProductNotFound(
        ProductNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)// htpp status = 404
                .body(exception.getMessage());// yazdıgımız hata mesajı
    }
}
