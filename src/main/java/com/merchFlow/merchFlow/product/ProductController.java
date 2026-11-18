package com.merchflow.product;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductDtos.Response> create(
            @Valid @RequestBody ProductDtos.Request request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<ProductDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }

    @PutMapping("/{id}")
    public ProductDtos.Response update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDtos.Request request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}