package com.merchflow.customer;

import jakarta.validation.constraints.*;

public final class CustomerDtos {

    private CustomerDtos() {}

    public record Request(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String phone,
        @NotBlank String address
    ) {}

    public record Response(
        Long id,
        String fullName,
        String email,
        String phone,
        String address
    ) {}
}