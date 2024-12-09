package org.example;

import org.example.utils.Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final Queue<Ticket> queue;
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TicketPool(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public int getTicketCount() {
        return queue.size();
    }

    public boolean addTickets(Ticket ticket) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                Utils.log("TicketPool at maximum capacity. Vendor is waiting to add tickets.");
                notFull.await(); // Wait until there is space to add more tickets
            }
            queue.add(ticket);
            Utils.log("Vendor "
                    + ticket.getVendorId()
                    + " added a ticket."
                    + "\nTotal tickets available: "
                    + queue.size(), Utils.CYAN);
            notEmpty.signalAll(); // Notify customers that tickets are available
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Ticket removeTicket(String customerId) throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                Utils.log("Ticket pool is empty. Customer is waiting for tickets.");
                notEmpty.await(); // Wait until tickets are available
            }
            Ticket ticket = queue.poll();
            if (ticket != null) {
                ticket.setCustomerId(customerId);
                Utils.log(customerId
                        + " purchased ticket "
                        + ticket.getTicketId()
                        + ". \nTotal tickets available: "
                        + queue.size(), Utils.YELLOW);

                // Notify the backend of the sale
                notifyBackend(new TicketSale(
                        ticket.getVendorId(),
                        customerId,
                        ticket.getTicketId(),
                        java.time.LocalDateTime.now()
                ));
            }

            notFull.signalAll(); // Notify vendors that space is available
            return ticket;
        } finally {
            lock.unlock();
        }
    }

    private void notifyBackend(TicketSale sale) {
        new Thread(() -> {
            try {
                HttpClient httpClient = HttpClient.newHttpClient();

                String jsonBody = String.format(
                        "{\"vendorId\":\"%s\",\"customerId\":\"%s\",\"ticketId\":\"%s\",\"timestamp\":\"%s\"}",
                        sale.getVendorId(),
                        sale.getCustomerId(),
                        sale.getTicketId(),
                        sale.getTimestamp()
                );

                Utils.log(jsonBody, Utils.BLUE);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/sales"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenAccept(response -> Utils.log("Backend response: " + response.body(), Utils.GREEN))
                        .exceptionally(error -> {
                            Utils.log("Failed to notify backend: " + error.getMessage(), Utils.RED);
                            return null;
                        });
            } catch (Exception e) {
                Utils.log("Error notifying backend: " + e.getMessage(), Utils.RED);
            }
        }).start(); // Run the backend notification in a separate thread
    }
}
