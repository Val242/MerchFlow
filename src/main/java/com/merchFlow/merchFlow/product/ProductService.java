package com.merchflow.product;

import com.merchflow.category.*;
import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository repository,
            CategoryRepository categoryRepository) {

        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductDtos.Response create(ProductDtos.Request request) {

        if (repository.existsBySku(request.sku())) {
            throw new BadRequestException("SKU already exists");
        }

        Category category = categoryRepository
            .findById(request.categoryId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Category not found")
            );

        Product product = new Product(
            request.sku(),
            request.name(),
            request.description(),
            request.price(),
            category
        );

        return map(repository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductDtos.Response> findAll() {
        return repository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductDtos.Response find(Long id) {
        return map(getProduct(id));
    }

    @Transactional
    public ProductDtos.Response update(
            Long id,
            ProductDtos.Request request) {

        Product product = getProduct(id);

        if (!product.getSku().equals(request.sku())
                && repository.existsBySku(request.sku())) {
            throw new BadRequestException("SKU already exists");
        }

        Category category = categoryRepository
            .findById(request.categoryId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Category not found")
            );

        product.setSku(request.sku());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(category);

        return map(repository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getProduct(id));
    }

    private Product getProduct(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Product not found: " + id
                )
            );
    }

    private ProductDtos.Response map(Product product) {
        return new ProductDtos.Response(
            product.getId(),
            product.getSku(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCategory().getId(),
            product.getCategory().getName(),
            product.isActive()
        );
    }
}