package com.merchflow.category;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoryDtos.Response> create(
            @Valid @RequestBody CategoryDtos.Request request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.create(request));
    }

    @GetMapping
    public List<CategoryDtos.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDtos.Response find(@PathVariable Long id) {
        return service.find(id);
    }

    @PutMapping("/{id}")
    public CategoryDtos.Response update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDtos.Request request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}