package com.oop.ticketingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    private String id;
    private String producedBy;
    private String purchasedBy;
    private LocalDateTime timeStamp;

    public Ticket() {}

    public Ticket(String id, String producedBy, String purchasedBy) {
        this.id = id;
        this.producedBy = producedBy;
        this.purchasedBy = purchasedBy;
        this.timeStamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public String getProducedBy() {
        return producedBy;
    }

    public void setProducedBy(String producedBy) {
        this.producedBy = producedBy;
    }
}
