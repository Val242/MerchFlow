package com.merchflow.shipment;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public final class ShipmentDtos {

    private ShipmentDtos() {}

    public record CreateRequest(
        @NotBlank String trackingNumber
    ) {}

    public record StatusRequest(
        ShipmentStatus status
    ) {}

    public record Response(
        Long id,
        Long orderId,
        String trackingNumber,
        ShipmentStatus status,
        Instant createdAt
    ) {}
}