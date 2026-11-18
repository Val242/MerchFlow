package com.merchflow.inventory;

import com.merchflow.product.Product;
import com.merchflow.warehouse.Warehouse;

import jakarta.persistence.*;

@Entity
@Table(
    name = "inventory",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_inventory_product_warehouse",
            columnNames = {"product_id", "warehouse_id"}
        )
    }
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private int quantityOnHand;

    @Column(nullable = false)
    private int quantityReserved;

    @Version
    private Long version;

    protected Inventory() {}

    public Inventory(Product product, Warehouse warehouse, int quantity) {
        this.product = product;
        this.warehouse = warehouse;
        this.quantityOnHand = quantity;
        this.quantityReserved = 0;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Warehouse getWarehouse() { return warehouse; }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public int getQuantityReserved() {
        return quantityReserved;
    }

    public int getAvailable() {
        return quantityOnHand - quantityReserved;
    }

    public void adjust(int quantity) {
        if (quantityOnHand + quantity < quantityReserved) {
            throw new IllegalArgumentException(
                "Adjustment cannot reduce stock below reserved quantity"
            );
        }

        if (quantityOnHand + quantity < 0) {
            throw new IllegalArgumentException(
                "Stock cannot be negative"
            );
        }

        quantityOnHand += quantity;
    }

    public void reserve(int quantity) {
        if (quantity <= 0 || getAvailable() < quantity) {
            throw new IllegalArgumentException(
                "Insufficient available stock"
            );
        }

        quantityReserved += quantity;
    }

    public void release(int quantity) {
        if (quantity <= 0 || quantityReserved < quantity) {
            throw new IllegalArgumentException(
                "Invalid stock release"
            );
        }

        quantityReserved -= quantity;
    }

    public void fulfill(int quantity) {
        if (quantity <= 0
                || quantityReserved < quantity
                || quantityOnHand < quantity) {
            throw new IllegalArgumentException(
                "Invalid fulfilment quantity"
            );
        }

        quantityReserved -= quantity;
        quantityOnHand -= quantity;
    }
}