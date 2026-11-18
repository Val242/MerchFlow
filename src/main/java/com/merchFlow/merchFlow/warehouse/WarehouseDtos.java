package com.merchflow.warehouse;

import jakarta.validation.constraints.NotBlank;

public final class WarehouseDtos {

    private WarehouseDtos() {}

    public record Request(
        @NotBlank String name,
        @NotBlank String address
    ) {}

    public record Response(
        Long id,
        String name,
        String address,
        boolean active
    ) {}
}