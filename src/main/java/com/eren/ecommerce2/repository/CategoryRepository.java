package com.eren.ecommerce2.repository;

import com.eren.ecommerce2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
}