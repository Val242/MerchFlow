package com.merchflow.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class OrderDtos {

    private OrderDtos() {}

    public record ItemRequest(
        @NotNull Long productId,
        @Min(1) int quantity
    ) {}

    public record CreateRequest(
        @NotNull Long customerId,
        @NotEmpty List<@Valid ItemRequest> items
    ) {}

    public record ItemResponse(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
    ) {}

    public record Response(
        Long id,
        Long customerId,
        OrderStatus status,
        BigDecimal totalAmount,
        Instant createdAt,
        List<ItemResponse> items
    ) {}
}