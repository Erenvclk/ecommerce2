package com.eren.ecommerce2.service;
import org.springframework.stereotype.Service;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
@RequiredArgsConstructor
@Service

public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getallproducts() {
        return productRepository.findAll();
    }

    public Product getproductbyid(Long id) {
        return productRepository.findById(id)
            .orElse(null);
    }
    
}
