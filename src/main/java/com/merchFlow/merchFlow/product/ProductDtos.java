package com.merchflow.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public final class ProductDtos {

    private ProductDtos() {}

    public record Request(
        @NotBlank String sku,
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @NotNull Long categoryId
    ) {}

    public record Response(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        boolean active
    ) {}
}