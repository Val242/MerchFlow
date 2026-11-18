package com.merchflow.payment;

import java.math.BigDecimal;
import java.time.Instant;

public final class PaymentDtos {

    private PaymentDtos() {}

    public record Request(
        boolean simulateSuccess
    ) {}

    public record Response(
        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentStatus status,
        String reference,
        Instant createdAt
    ) {}
}