package com.oop.ticketingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Ticket {

    @Id
    private String id;
    private String producedBy;
    private String purchasedBy;

    public Ticket() {}

    public Ticket(String id, String producedBy, String purchasedBy) {
        this.id = id;
        this.producedBy = producedBy;
        this.purchasedBy = purchasedBy;
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
