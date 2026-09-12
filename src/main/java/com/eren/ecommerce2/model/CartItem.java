package com.eren.ecommerce2.model;

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

// bir tane cart nesnesi içinde birden çok kartitem var
// cunku urun + urun adeti şeklinde tutmanın yolu bu
// 
public class CartItem {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne // bu ilişkiler soyle çalısıyor. 
    /*
        a sayfasında @manytoone yazdın
        hangi ürün ile ile ilişkili oldugunu 
        private b b; şeklinde anlatıyorsun.
        ilişki de yazılan sayfadan  -> nesnesini olusturdugun sayfaya yanı nesneye dogru


        @joın column -> bu ilişkinedirdiğin nesneyi 
        foregin key olarak hangı isimle tutacağını belirtiyor

        yanı a sayfasındasın
        manytoone b dedin
        joıncolumn buyuk ihtimal b_id ; gibi bir şey olur
    */
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
