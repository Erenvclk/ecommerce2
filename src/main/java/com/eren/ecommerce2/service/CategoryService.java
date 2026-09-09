package com.eren.ecommerce2.service;

import com.eren.ecommerce2.dto.CategoryRequest;
import com.eren.ecommerce2.dto.CategoryResponse;
import com.eren.ecommerce2.mapper.CategoryMapper;
import com.eren.ecommerce2.model.Category;
import com.eren.ecommerce2.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /*
     * category için repository yazmadık
     * requestte gelen id değerini dt deki gerçek category tablosundan
     * id değişkenine atadık
     * bunu referans da verebilirdik ya da repository de yazabilirdik
     * ama service üzerinden çekmek proje gelişiminde daha mantıklı
     * category service üzerinden baska seyler de yazılabilir.
     * sadece service üzerinden erişilme ek zahmeti var.
     */
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();

        return CategoryMapper.toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = CategoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.setName(request.name());

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow();

        categoryRepository.delete(category);
    } 
}