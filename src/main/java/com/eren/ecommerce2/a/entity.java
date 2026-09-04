//package belirt
package com.eren.ecommerce2.a;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private BigDecimal price;
    private String description;
    private int stock;
}
