package com.eren.ecommerce2.mapper;
import com.eren.ecommerce2.dto.CategoryRequest;
import com.eren.ecommerce2.dto.CategoryResponse;
import com.eren.ecommerce2.model.Category; 

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}