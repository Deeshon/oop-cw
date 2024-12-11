package org.example;

import org.example.utils.Utils;

public class Ticket {

    final String ticketId;
    private String vendorId;
    private String customerId;

    public Ticket(String vendorId, String customerId) {
        this.ticketId = vendorId + "-" + "TIC   " + Utils.generateId();
        this.vendorId = vendorId;
        this.customerId = customerId;
    }

    public String getTicketId() {return ticketId;}

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
