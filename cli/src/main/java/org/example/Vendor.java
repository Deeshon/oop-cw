package org.example;

import org.example.utils.Utils;

public class Vendor implements Runnable {
    private final String vendorId;
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int totalTickets;
    private boolean running = true;

    public Vendor(String vendorName, TicketPool ticketPool, int releaseRate, int totalTickets) {
        this.vendorId = vendorName + Utils.generateId();
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        int addedTickets = 0;

        while (running && addedTickets < totalTickets) {
            if (ticketPool.addTickets(new Ticket("event1202", vendorId, ""))) {
//                Utils.log("Vendor " + vendorId + " added a ticket.");
//                Utils.log("Ticket added to the pool. Total tickets: " + ticketPool.getTicketCount(), Utils.CYAN, "TicketPool");
                addedTickets++;
            }

            try {
                Thread.sleep(releaseRate * 1000L); // Simulate release rate
            } catch (InterruptedException e) {
                Utils.log(vendorId + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
