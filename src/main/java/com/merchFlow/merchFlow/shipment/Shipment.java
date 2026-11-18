package com.merchflow.shipment;

import com.merchflow.order.CustomerOrder;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
    name = "shipments",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_shipment_order",
        columnNames = "order_id"
    )
)
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "order_id",
        nullable = false,
        unique = true
    )
    private CustomerOrder order;

    @Column(nullable = false, unique = true, length = 100)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShipmentStatus status = ShipmentStatus.CREATED;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Shipment() {}

    public Shipment(CustomerOrder order, String trackingNumber) {
        this.order = order;
        this.trackingNumber = trackingNumber;
    }

    public Long getId() { return id; }
    public CustomerOrder getOrder() { return order; }
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public ShipmentStatus getStatus() { return status; }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() { return createdAt; }
}