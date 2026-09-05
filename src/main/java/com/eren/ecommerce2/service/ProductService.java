package com.eren.ecommerce2.service;

import org.springframework.stereotype.Service;

import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.exception.ProductNotFoundException;
import com.eren.ecommerce2.mapper.ProductMapper;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {

        Product product = ProductMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }
 // tolistin çıktısı prodcut response listesi olucak
 //product repositorsi product entitylerine bağlı
// product ın tüm fieldlarını al -> productrepositort.finall() yapıyor bunu
// hepsını productmapper da ki to response ile çevir
// çevirilien product repspnse listesinde

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
// id ye göre productları çekip product responsa atıyorsun
// 
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toResponse(product);
    }
}