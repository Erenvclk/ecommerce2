package com.eren.ecommerce2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eren.ecommerce2.model.Cart;

@Repository

public interface CartRepository extends JpaRepository<Cart, Long> {

}
