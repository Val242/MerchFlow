package com.merchflow.warehouse;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WarehouseDtos.Response> create(
            @Valid @RequestBody WarehouseDtos.Request request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<WarehouseDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WarehouseDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }

    @PutMapping("/{id}")
    public WarehouseDtos.Response update(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDtos.Request request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}