package com.oop.ticketingSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, name=%s]",
                id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
