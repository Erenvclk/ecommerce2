package com.eren.ecommerce2.exception;

// Product exceptionu yazdık ama http yanıtı olarak 
//gosterilmesi için bu sayfaya ihtiyac var
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice // bu class global hata ayıklayıcısıdır

public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class) // sadece bu hata türünü yakala
    public ResponseEntity<String> handleProductNotFound(
            ProductNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)// htpp status = 404
                .body(exception.getMessage());// yazdıgımız hata mesajı
    }
    // @ valid bir isterği reddettiğinde springden gelen hataları json donucek
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}
