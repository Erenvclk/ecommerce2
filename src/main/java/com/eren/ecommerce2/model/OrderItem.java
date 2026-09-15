package com.eren.ecommerce2.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    // OrderItem satırının kendi id'si
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bu satır hangi siparişe ait?
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Bu satır hangi ürünü temsil ediyor?
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Bu üründen kaç tane sipariş edildi?
    private Integer quantity;

    // Sipariş verildiği ANDAKİ ürün fiyatı
    private BigDecimal unitPrice;

    // unitPrice * quantity
    private BigDecimal lineTotal;
}