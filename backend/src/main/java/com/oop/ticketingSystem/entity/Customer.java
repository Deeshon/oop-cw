package com.oop.ticketingSystem.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private String id;
    private String name;

    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = "CUST-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, name=%s]",
                id, name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
