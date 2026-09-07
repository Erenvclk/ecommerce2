package com.eren.ecommerce2.dto;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotBlank
    String name,

    String description,

   @NotNull
   @DecimalMin(value = "0.0", inclusive = false) // sıfırdan buyuk ve sıfır da kabul değil
    BigDecimal price,

    @NotNull
    @Min(0)
    Integer stock
) {
    // anotasyonların calısması için controllara da yazmak lazım
    
}
