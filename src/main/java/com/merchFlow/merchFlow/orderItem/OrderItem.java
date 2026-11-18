package com.merchflow.order;

import com.merchflow.product.Product;
import com.merchflow.inventory.Inventory;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    protected OrderItem() {}

    public OrderItem(
            Product product,
            int quantity,
            Inventory inventory) {

        this.product = product;
        this.quantity = quantity;
        this.inventory = inventory;
        this.unitPrice = product.getPrice();
    }

    void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Inventory getInventory() { return inventory; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(
            BigDecimal.valueOf(quantity)
        );
    }
}