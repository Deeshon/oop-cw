package org.example;

import org.example.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TicketPoolTests {

    private TicketPool ticketPool;

    @BeforeEach
    void setUp() {
        ticketPool = new TicketPool(5); // Initialize with a capacity of 5
    }

    @Test
    void testAddTicketWhenPoolIsEmpty() {
        Ticket ticket = new Ticket("Vendor-1", "Ticket-1");
        boolean result = ticketPool.addTickets(ticket);
        assertTrue(result, "Ticket should be added successfully when the pool is empty.");
        assertEquals(1, ticketPool.getTicketCount(), "Ticket count should be 1.");
    }

    @Test
    void testAddTicketsUntilFull() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            Ticket ticket = new Ticket("Vendor-" + i, "Ticket-" + i);
            boolean result = ticketPool.addTickets(ticket);
            assertTrue(result, "Ticket should be added successfully.");
        }

        Ticket extraTicket = new Ticket("Vendor-Extra", "Ticket-Extra");

        Thread vendorThread = new Thread(() -> {
            boolean result = ticketPool.addTickets(extraTicket);
            assertTrue(result, "Vendor should successfully add ticket after capacity is freed.");
        });

        vendorThread.start();

        // Simulate a customer purchasing a ticket to free space
        Thread customerThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate some delay
                ticketPool.removeTicket("Customer-1");
            } catch (InterruptedException e) {
                fail("Customer thread interrupted.");
            }
        });

        customerThread.start();

        vendorThread.join();
        customerThread.join();

        assertEquals(5, ticketPool.getTicketCount(), "Ticket count should be 5 after capacity is managed.");
    }

    @Test
    void testRemoveTicketWhenPoolHasTickets() throws InterruptedException {
        Ticket ticket = new Ticket("Vendor-1", "Ticket-1");
        ticketPool.addTickets(ticket);

        Ticket removedTicket = ticketPool.removeTicket("Customer-1");
        assertNotNull(removedTicket, "Customer should successfully remove a ticket.");
        assertEquals("Customer-1", removedTicket.getCustomerId(), "Removed ticket should be assigned to the correct customer.");
        assertEquals(0, ticketPool.getTicketCount(), "Ticket pool should be empty after removal.");
    }

    @Test
    void testMultipleCustomersAccessingPool() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            Ticket ticket = new Ticket("Vendor-1", "Ticket-" + i);
            ticketPool.addTickets(ticket);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 3; i++) {
            String customerId = "Customer-" + i;
            executorService.execute(() -> {
                try {
                    Ticket removedTicket = ticketPool.removeTicket(customerId);
                    assertNotNull(removedTicket, "Customer should successfully remove a ticket.");
                    assertEquals(customerId, removedTicket.getCustomerId(), "Removed ticket should be assigned to the correct customer.");
                } catch (InterruptedException e) {
                    fail("Customer thread interrupted.");
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(2, ticketPool.getTicketCount(), "Two tickets should remain in the pool after 3 customers purchase tickets.");
    }

    @Test
    void testCustomerWaitsWhenNoTicketsAvailable() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            try {
                Ticket removedTicket = ticketPool.removeTicket("Customer-1");
                assertNotNull(removedTicket, "Customer should eventually remove a ticket.");
                assertEquals("Customer-1", removedTicket.getCustomerId(), "Removed ticket should be assigned to the correct customer.");
            } catch (InterruptedException e) {
                fail("Customer thread interrupted.");
            }
        });

        executorService.execute(() -> {
            try {
                Thread.sleep(2000); // Simulate some delay before adding tickets
                Ticket ticket = new Ticket("Vendor-1", "Ticket-1");
                ticketPool.addTickets(ticket);
            } catch (InterruptedException e) {
                fail("Vendor thread interrupted.");
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("Test interrupted.");
        }

        assertEquals(0, ticketPool.getTicketCount(), "Ticket pool should be empty after one customer removes the only ticket.");
    }
}
