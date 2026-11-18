package com.merchflow.inventory;

import com.merchflow.product.*;
import com.merchflow.warehouse.*;
import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryService(
            InventoryRepository repository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {

        this.repository = repository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public InventoryDtos.Response create(
            InventoryDtos.CreateRequest request) {

        Product product = productRepository
            .findById(request.productId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Product not found")
            );

        Warehouse warehouse = warehouseRepository
            .findById(request.warehouseId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Warehouse not found")
            );

        if (repository.findByProductIdAndWarehouseId(
                product.getId(), warehouse.getId()).isPresent()) {

            throw new BadRequestException(
                "Inventory record already exists"
            );
        }

        Inventory inventory = new Inventory(
            product,
            warehouse,
            request.initialQuantity()
        );

        return map(repository.save(inventory));
    }

    @Transactional(readOnly = true)
    public List<InventoryDtos.Response> findAll() {
        return repository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    @Transactional(readOnly = true)
    public InventoryDtos.Response find(Long id) {
        return map(getInventory(id));
    }

    @Transactional
    public InventoryDtos.Response adjust(
            Long id,
            InventoryDtos.AdjustRequest request) {

        Inventory inventory = getInventory(id);

        inventory.adjust(request.quantityChange());

        return map(repository.save(inventory));
    }

    private Inventory getInventory(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Inventory record not found: " + id
                )
            );
    }

    private InventoryDtos.Response map(Inventory inventory) {
        return new InventoryDtos.Response(
            inventory.getId(),
            inventory.getProduct().getId(),
            inventory.getProduct().getName(),
            inventory.getWarehouse().getId(),
            inventory.getWarehouse().getName(),
            inventory.getQuantityOnHand(),
            inventory.getQuantityReserved(),
            inventory.getAvailable()
        );
    }
}