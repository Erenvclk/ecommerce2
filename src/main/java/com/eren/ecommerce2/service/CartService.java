// CartService.java

package com.eren.ecommerce2.service;

import com.eren.ecommerce2.model.Cart;
import com.eren.ecommerce2.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart createCart() {

        // Yeni, boş bir sepet oluştur.
        Cart cart = new Cart();

        // DB'ye kaydet.
        // id burada oluşur.
        return cartRepository.save(cart);
    }
}