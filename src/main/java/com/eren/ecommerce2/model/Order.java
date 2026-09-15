package com.eren.ecommerce2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

// "order" SQL'de özel bir kelime olabildiği için
// tablo adını "orders" yapıyoruz.
@Table(name = "orders")

@Getter
@Setter
@NoArgsConstructor
public class Order {

    // Siparişin kendi id'si
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Siparişin genel toplamı
    // Örn: 5000.00
    private BigDecimal totalPrice;

    // Siparişin durumu
    // Şimdilik String tutuyoruz.
    // Örn: PENDING, COMPLETED
    // private String status;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Siparişin oluşturulduğu zaman
    private LocalDateTime createdAt;
}