package com.merchflow.category;

import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryDtos.Response create(CategoryDtos.Request request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new BadRequestException(
                "Category name already exists"
            );
        }

        Category category = new Category(
            request.name(),
            request.description()
        );

        return map(repository.save(category));
    }

    public List<CategoryDtos.Response> findAll() {
        return repository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    public CategoryDtos.Response find(Long id) {
        return map(getCategory(id));
    }

    public CategoryDtos.Response update(
            Long id,
            CategoryDtos.Request request) {

        Category category = getCategory(id);

        category.setName(request.name());
        category.setDescription(request.description());

        return map(repository.save(category));
    }

    public void delete(Long id) {
        repository.delete(getCategory(id));
    }

    private Category getCategory(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Category not found: " + id
                )
            );
    }

    private CategoryDtos.Response map(Category category) {
        return new CategoryDtos.Response(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }
}