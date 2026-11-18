package com.merchflow.shipment;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PostMapping("/api/v1/orders/{orderId}/shipments")
    public ResponseEntity<ShipmentDtos.Response> create(
            @PathVariable Long orderId,
            @Valid @RequestBody ShipmentDtos.CreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(orderId, request));
    }

    @GetMapping("/api/v1/orders/{orderId}/shipment")
    public ShipmentDtos.Response findByOrder(
            @PathVariable Long orderId) {

        return service.findByOrder(orderId);
    }

    @PatchMapping("/api/v1/shipments/{id}/status")
    public ShipmentDtos.Response updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentDtos.StatusRequest request) {

        return service.updateStatus(id, request);
    }
}