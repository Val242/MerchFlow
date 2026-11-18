package com.merchflow.payment;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/api/v1/orders/{orderId}/payments")
    public ResponseEntity<PaymentDtos.Response> pay(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentDtos.Request request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.pay(orderId, request));
    }

    @GetMapping("/api/v1/orders/{orderId}/payments")
    public List<PaymentDtos.Response> findByOrder(
            @PathVariable Long orderId) {

        return service.findByOrder(orderId);
    }

    @GetMapping("/api/v1/payments/{id}")
    public PaymentDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }
}