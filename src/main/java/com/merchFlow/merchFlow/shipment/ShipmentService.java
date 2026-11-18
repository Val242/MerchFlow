package com.merchflow.shipment;

import com.merchflow.order.*;
import com.merchflow.inventory.*;
import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CustomerOrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            CustomerOrderRepository orderRepository,
            InventoryRepository inventoryRepository) {

        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public ShipmentDtos.Response create(
            Long orderId,
            ShipmentDtos.CreateRequest request) {

        CustomerOrder order = orderRepository
            .findById(orderId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Order not found")
            );

        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new BadRequestException(
                "Only confirmed orders can be shipped"
            );
        }

        if (shipmentRepository.findByOrderId(orderId).isPresent()) {
            throw new BadRequestException(
                "Shipment already exists"
            );
        }

        for (OrderItem item : order.getItems()) {

            Inventory stock = item.getInventory();

            stock.fulfill(item.getQuantity());

            inventoryRepository.save(stock);
        }

        order.setStatus(OrderStatus.SHIPPED);

        Shipment shipment = new Shipment(
            order,
            request.trackingNumber()
        );

        return map(shipmentRepository.save(shipment));
    }

    @Transactional
    public ShipmentDtos.Response updateStatus(
            Long id,
            ShipmentDtos.StatusRequest request) {

        Shipment shipment = shipmentRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Shipment not found")
            );

        shipment.setStatus(request.status());

        if (request.status() == ShipmentStatus.DELIVERED) {
            shipment.getOrder().setStatus(OrderStatus.DELIVERED);
        }

        return map(shipmentRepository.save(shipment));
    }

    @Transactional(readOnly = true)
    public ShipmentDtos.Response findByOrder(Long orderId) {

        Shipment shipment = shipmentRepository
            .findByOrderId(orderId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Shipment not found for order"
                )
            );

        return map(shipment);
    }

    private ShipmentDtos.Response map(Shipment shipment) {
        return new ShipmentDtos.Response(
            shipment.getId(),
            shipment.getOrder().getId(),
            shipment.getTrackingNumber(),
            shipment.getStatus(),
            shipment.getCreatedAt()
        );
    }
}