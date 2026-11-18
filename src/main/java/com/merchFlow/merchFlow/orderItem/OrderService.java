package com.merchflow.order;

import com.merchflow.customer.*;
import com.merchflow.product.*;
import com.merchflow.inventory.*;
import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(
            CustomerOrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            InventoryRepository inventoryRepository) {

        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public OrderDtos.Response create(
            OrderDtos.CreateRequest request) {

        Customer customer = customerRepository
            .findById(request.customerId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Customer not found")
            );

        CustomerOrder order = new CustomerOrder(customer);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderDtos.ItemRequest item : request.items()) {

            Product product = productRepository
                .findById(item.productId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Product not found: " + item.productId()
                    )
                );

            if (!product.isActive()) {
                throw new BadRequestException(
                    "Product is inactive"
                );
            }

            Inventory stock = inventoryRepository.findAll()
                .stream()
                .filter(inventory ->
                    inventory.getProduct().getId()
                        .equals(product.getId())
                    && inventory.getAvailable() >= item.quantity()
                )
                .findFirst()
                .orElseThrow(() ->
                    new BadRequestException(
                        "Insufficient stock for product "
                        + product.getId()
                    )
                );

            stock.reserve(item.quantity());

            inventoryRepository.save(stock);

            OrderItem orderItem = new OrderItem(
                product,
                item.quantity(),
                stock
            );

            order.addItem(orderItem);

            total = total.add(orderItem.getLineTotal());
        }

        order.setTotalAmount(total);

        return map(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderDtos.Response> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    @Transactional(readOnly = true)
    public OrderDtos.Response find(Long id) {
        return map(getOrder(id));
    }

    @Transactional
    public OrderDtos.Response cancel(Long id) {

        CustomerOrder order = getOrder(id);

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT
                && order.getStatus() != OrderStatus.CONFIRMED) {

            throw new BadRequestException(
                "Order cannot be cancelled at this stage"
            );
        }

        for (OrderItem item : order.getItems()) {

            Inventory stock = item.getInventory();

            stock.release(item.getQuantity());

            inventoryRepository.save(stock);
        }

        order.setStatus(OrderStatus.CANCELLED);

        return map(orderRepository.save(order));
    }

    CustomerOrder getOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Order not found: " + id
                )
            );
    }

    OrderDtos.Response map(CustomerOrder order) {

        List<OrderDtos.ItemResponse> items =
            order.getItems().stream()
                .map(item ->
                    new OrderDtos.ItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getLineTotal()
                    )
                )
                .toList();

        return new OrderDtos.Response(
            order.getId(),
            order.getCustomer().getId(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getCreatedAt(),
            items
        );
    }
}