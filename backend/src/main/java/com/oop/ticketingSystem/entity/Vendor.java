package com.oop.ticketingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    private String id;
    private String name;

    public Vendor() {}

    public Vendor(String name) {
        this.name = name;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = "VEN-" + UUID.randomUUID().toString().substring(0,8);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
