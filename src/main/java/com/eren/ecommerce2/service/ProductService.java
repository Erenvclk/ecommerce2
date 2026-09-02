package com.eren.ecommerce2.service;

import org.springframework.stereotype.Service;

import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.mapper.ProductMapper;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {

        Product product = ProductMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }
}