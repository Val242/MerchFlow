package com.merchflow.order;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderDtos.Response> create(
            @Valid @RequestBody OrderDtos.CreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<OrderDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }

    @PatchMapping("/{id}/cancel")
    public OrderDtos.Response cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}