package com.eren.ecommerce2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter 
@Setter  
@NoArgsConstructor 


public class Cart {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;    
}
