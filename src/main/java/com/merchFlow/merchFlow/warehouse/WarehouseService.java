package com.merchflow.warehouse;

import com.merchflow.common.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository repository;

    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    public WarehouseDtos.Response create(WarehouseDtos.Request request) {

        Warehouse warehouse = new Warehouse(
            request.name(),
            request.address()
        );

        return map(repository.save(warehouse));
    }

    public List<WarehouseDtos.Response> findAll() {
        return repository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    public WarehouseDtos.Response find(Long id) {
        return map(getWarehouse(id));
    }

    public WarehouseDtos.Response update(
            Long id,
            WarehouseDtos.Request request) {

        Warehouse warehouse = getWarehouse(id);

        warehouse.setName(request.name());
        warehouse.setAddress(request.address());

        return map(repository.save(warehouse));
    }

    public void delete(Long id) {
        repository.delete(getWarehouse(id));
    }

    private Warehouse getWarehouse(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Warehouse not found: " + id
                )
            );
    }

    private WarehouseDtos.Response map(Warehouse warehouse) {
        return new WarehouseDtos.Response(
            warehouse.getId(),
            warehouse.getName(),
            warehouse.getAddress(),
            warehouse.isActive()
        );
    }
}