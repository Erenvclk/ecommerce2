package com.eren.ecommerce2.a;
import org.springframework.stereotype.Service;
import com.eren.ecommerce2.a.repository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import com.eren.ecommerce2.a.entity;
@RequiredArgsConstructor
@Service

public class service {
        private final repository repository;

        public List<entity> getAllEntities() {
            return repository.findAll();
        }

        public entity getEntityById(Long id) {
            return repository.findById(id).orElse(null);
        }
   
    }
