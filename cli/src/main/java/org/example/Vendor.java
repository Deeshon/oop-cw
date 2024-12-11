package org.example;

import org.example.utils.Utils;

public class Vendor implements Runnable {
    private final String vendorId;
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int totalTickets;
    private boolean running = true;

    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets) {
        this.vendorId = "VEN-" + Utils.generateId();
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        int addedTickets = 0;

        while (running && addedTickets < totalTickets) {
            if (ticketPool.addTickets(new Ticket( vendorId, ""))) {
                addedTickets++;
            }

            try {
                Thread.sleep(releaseRate * 1000L); // Simulate release rate
            } catch (InterruptedException e) {
                Utils.log(vendorId + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }

        // Log message when the vendor finishes selling tickets
        if (addedTickets >= totalTickets) {
            Utils.log(vendorId + " has finished selling all tickets.");
        }

        // Stop the thread after finishing
        running = false;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
