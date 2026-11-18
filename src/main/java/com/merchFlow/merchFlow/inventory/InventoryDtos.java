package com.merchflow.inventory;

import jakarta.validation.constraints.*;

public final class InventoryDtos {

    private InventoryDtos() {}

    public record CreateRequest(
        @NotNull Long productId,
        @NotNull Long warehouseId,
        @Min(0) int initialQuantity
    ) {}

    public record AdjustRequest(
        @NotNull Integer quantityChange
    ) {}

    public record Response(
        Long id,
        Long productId,
        String productName,
        Long warehouseId,
        String warehouseName,
        int quantityOnHand,
        int quantityReserved,
        int available
    ) {}
}