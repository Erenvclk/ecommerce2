package com.eren.ecommerce2.mapper;
import com.eren.ecommerce2.dto.ProductRequest;
import com.eren.ecommerce2.dto.ProductResponse;
import com.eren.ecommerce2.model.Product;

// toEntity: ProductRequest -> Product
// toResponse: Product -> ProductResponse işlemlerini yapıyor.
public class ProductMapper {
    public static Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
