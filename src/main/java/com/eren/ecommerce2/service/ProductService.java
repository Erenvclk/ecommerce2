package com.eren.ecommerce2.service;

import org.springframework.stereotype.Service;

import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.exception.ProductNotFoundException;
import com.eren.ecommerce2.mapper.ProductMapper;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.ProductRepository;

import com.eren.ecommerce2.model.Category;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductResponse createProduct(ProductRequest request) {

        Category category = categoryService.getCategoryById(request.categoryId());

        Product product = ProductMapper.toEntity(request);

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }
    // tolistin çıktısı prodcut response listesi olucak
    // product repositorsi product entitylerine bağlı
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
    // var olan ürünü bul
    // yenı değerleri üstüne yaz
    // dt ue kaydet
    // kayıtlı urunu response cevır dpn

    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }

    // return etmeyen fonk -> void fonk
    // static -> tüm projede geçerli,
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}