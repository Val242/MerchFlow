package com.merchflow.inventory;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventoryDtos.Response> create(
            @Valid @RequestBody InventoryDtos.CreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<InventoryDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InventoryDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }

    @PatchMapping("/{id}/adjust")
    public InventoryDtos.Response adjust(
            @PathVariable Long id,
            @Valid @RequestBody InventoryDtos.AdjustRequest request) {

        return service.adjust(id, request);
    }
}