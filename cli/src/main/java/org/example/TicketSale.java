package org.example;

import java.time.LocalDateTime;

public class TicketSale {
    private final String vendorId;
    private final String customerId;
    private final String ticketId;
    private final LocalDateTime timestamp;

    public TicketSale(String vendorId, String customerId, String ticketId, LocalDateTime timestamp) {
        this.vendorId = vendorId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.timestamp = timestamp;
    }

    public String getVendorId() { return vendorId; }
    public String getCustomerId() { return customerId; }
    public String getTicketId() { return ticketId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
