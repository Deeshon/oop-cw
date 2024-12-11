package org.example;

import org.example.utils.Utils;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String customerId;
    private final int retrievalRate;
    private boolean running = true;

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.customerId = "CUST-"+ Utils.generateId();
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Ticket ticket = ticketPool.removeTicket(customerId);
                if (ticket == null) {
                    Utils.log(customerId + " found no tickets available.");
                }
                Thread.sleep(retrievalRate * 1000L); // Simulate retrieval rate
            } catch (InterruptedException e) {
                Utils.log(customerId + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void stop() {
        running = false;
    }
}
