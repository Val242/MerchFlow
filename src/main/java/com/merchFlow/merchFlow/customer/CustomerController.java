package com.merchflow.customer;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerDtos.Response> create(
            @Valid @RequestBody CustomerDtos.Request request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<CustomerDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }
}