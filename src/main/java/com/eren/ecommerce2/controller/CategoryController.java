package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.CategoryRequest;
import com.eren.ecommerce2.dto.CategoryResponse;
import com.eren.ecommerce2.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(
            // requst nesnesi burada olusuyor.
            @RequestBody CategoryRequest request)
    // http bpdysındekı veriyi al,
    // categoryRequest tipinden request değişkenine koy.
    // service üzerine işlem yaptıgın request değişkeni burada doluyor.

    {

        return categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);
    }
}