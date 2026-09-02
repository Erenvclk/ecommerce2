package com.eren.ecommerce2.mapper;
import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.model.Product;


public class ProductMapper {
    public static toEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        return product;
    }

    public static ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}
