package org.example;

import org.example.utils.Utils;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String name;
    private final String customerId;
    private final int retrievalRate;
    private boolean running = true;

    public Customer(TicketPool ticketPool, String name, int retrievalRate) {
        this.customerId = "CUST-"+ Utils.generateId();
        this.ticketPool = ticketPool;
        this.name = name;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Ticket ticket = ticketPool.removeTicket(customerId);
                if (ticket == null) {
                    Utils.log(name + " found no tickets available.");
                }
                Thread.sleep(retrievalRate * 1000L); // Simulate retrieval rate
            } catch (InterruptedException e) {
                Utils.log(name + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
