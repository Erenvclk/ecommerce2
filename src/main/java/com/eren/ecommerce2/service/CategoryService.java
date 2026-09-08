package com.eren.ecommerce2.service;

import com.eren.ecommerce2.model.Category;
import com.eren.ecommerce2.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    /*
    category için repository yazmadık
    requestte gelen id değerini dt deki gerçek category tablosundan
     id değişkenine atadık
     bunu referans da verebilirdik ya da repository de yazabilirdik 
     ama service üzerinden çekmek proje gelişiminde daha mantıklı
     category service üzerinden baska seyler de yazılabilir. 
     sadece service üzerinden erişilme ek zahmeti var.
    */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow();
    }
}