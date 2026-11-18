package com.merchflow.customer;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Customer() {}

    public Customer(String fullName, String email,
                    String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String value) {
        fullName = value;
    }
    public String getEmail() { return email; }
    public void setEmail(String value) {
        email = value;
    }
    public String getPhone() { return phone; }
    public void setPhone(String value) {
        phone = value;
    }
    public String getAddress() { return address; }
    public void setAddress(String value) {
        address = value;
    }
}