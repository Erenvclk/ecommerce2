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
                product.getStock(),
                //bunun boyle olmasının sebebi : 
                /* 
                ProductResponse nesnesinin parametre türü product. 
                biz de product içinde 
                    private category category yazdık
                    yanı product içinde category nesnesi var.

                    product.getCategory ile once 
                    product içindeki category nesnesine 
                    sonra onun içindeki fieldlara erişiyoruz.


                */

                    /*
                    burada direkt 
                    Category category = new Category 
                     de diyebilirdik. 
                     ama prdouct içinden yapmanın daha mantıklı olgunu soyluyor ai
                    */
                product.getCategory().getId(),
                product.getCategory().getName()
                
        );
    }
}
