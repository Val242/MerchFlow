package com.merchflow.payment;

import com.merchflow.order.*;
import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerOrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            CustomerOrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public PaymentDtos.Response pay(
            Long orderId,
            PaymentDtos.Request request) {

        CustomerOrder order = orderRepository
            .findById(orderId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Order not found")
            );

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new BadRequestException(
                "Order is not awaiting payment"
            );
        }

        PaymentStatus status = request.simulateSuccess()
            ? PaymentStatus.SUCCESS
            : PaymentStatus.FAILED;

        Payment payment = new Payment(
            order,
            order.getTotalAmount(),
            status,
            "SIM-" + UUID.randomUUID()
        );

        payment = paymentRepository.save(payment);

        if (status == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.CONFIRMED);
        }

        return map(payment);
    }

    public List<PaymentDtos.Response> findByOrder(Long orderId) {

        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found");
        }

        return paymentRepository.findByOrderId(orderId)
            .stream()
            .map(this::map)
            .toList();
    }

    public PaymentDtos.Response find(Long id) {

        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Payment not found")
            );

        return map(payment);
    }

    private PaymentDtos.Response map(Payment payment) {
        return new PaymentDtos.Response(
            payment.getId(),
            payment.getOrder().getId(),
            payment.getAmount(),
            payment.getStatus(),
            payment.getReference(),
            payment.getCreatedAt()
        );
    }
}