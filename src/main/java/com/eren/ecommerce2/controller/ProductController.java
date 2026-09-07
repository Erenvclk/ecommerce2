package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController // http endpointlerını alacak controller sayfasu
// oldgunu bu belirtiyor
@RequestMapping("/products") // bu controller
// products endpointine gelen htpp isteklerini
// service iletecek
@RequiredArgsConstructor // bu zaten gerekli constructorları kullnaman içindi

public class ProductController {

    private final ProductService productService;

    @PostMapping // bu endpointe POST isteği gelirse bunu çalıstır.
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        // @valid -> jsonu requeste çevirdikten sonra validiton kurallarını kontrol et
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // bu endpoine get isteği gelirse
    // url deki id değerini al
    // service e gonder
    // serviceten gelen Product Responsu cliente ver
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable long id) {// @pathvarible Long id -> urldel
        // id değerini al ve long id değişkenine koy
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
