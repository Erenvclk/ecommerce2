package com.eren.ecommerce2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eren.ecommerce2.dto.OrderResponse;
import com.eren.ecommerce2.dto.UpdateOrderStatusRequest;
import com.eren.ecommerce2.service.OrderService;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    /*
     * Controller, siparişin business logic'ini kendisi yapmaz.
     *
     * Görevi:
     *
     * HTTP isteğini almak
     * ↓
     * gerekli veriyi çıkarmak
     * ↓
     * OrderService'e göndermek
     * ↓
     * service'in döndürdüğü response'u kullanıcıya vermek
     */
    private final OrderService orderService;

    // ------------------------------------------------
    // PSEUDOCODE:
    //
    // Kullanıcı bir sepeti siparişe dönüştürmek istiyor.
    //
    // Örnek:
    //
    // POST /orders/from-cart/1
    //
    // Buradaki 1 = cartId
    // ------------------------------------------------

    @PostMapping("/from-cart/{cartId}")
    public OrderResponse createOrder(
            @PathVariable Long cartId) {

        // --------------------------------------------
        // 1. URL'deki cartId'yi aldık.
        //
        // Örneğin:
        //
        // /orders/from-cart/1
        //
        // cartId = 1
        // --------------------------------------------

        // --------------------------------------------
        // 2. cartId'yi OrderService'e gönder.
        //
        // Bundan sonrası service'in işi:
        //
        // - CartItem'ları bul
        // - Order oluştur
        // - OrderItem oluştur
        // - toplam hesapla
        // - stok düş
        // - kayıtları kaydet
        // - sepeti temizle
        // --------------------------------------------

        return orderService.createOrder(cartId);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(
            @PathVariable Long orderId) {

        return orderService.getOrder(orderId);
    }

    @PatchMapping("/{orderId}/status")
    public OrderResponse updateStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {

        return orderService.updateStatus(
                orderId,
                request);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {

        return orderService.getAllOrders();
    }
}