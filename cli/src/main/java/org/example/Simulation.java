package org.example;

import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private TicketPool ticketPool;
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();

    public synchronized void startSimulation(int numberOfVendors, int numberOfCustomers, TicketPool ticketPool, Configuration config ) {
        // Initialize the ticket pool
        this.ticketPool = ticketPool;

        // Clear the existing vendor/customer lists to restart the simulation
        vendors.clear();
        vendorThreads.clear();
        customers.clear();
        customerThreads.clear();

        // Create vendor threads
        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor" + i, ticketPool, config.getTicketReleaseRate(), config.getTotalTickets());
            vendors.add(vendor);
            vendorThreads.add(new Thread(vendor, "Vendor" + i));
        }

        // Create customer threads
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer(ticketPool, "Customer" + i, config.getCustomerRetrievalRate());
            customers.add(customer);
            customerThreads.add(new Thread(customer, "Customer" + i));
        }

        System.out.println("Starting simulation...");

        // Start all vendor threads with a delay
        for (Thread vendorThread : vendorThreads) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Utils.log("Error introducing delay for vendor thread: " + e.getMessage());
            }
            vendorThread.start();
            Utils.log("New vendor [" + vendorThread.getName() + "] entered the system.", Utils.GREEN);
        }

        try {
            // Wait for 2 seconds before starting customer threads
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Start all customer threads with a delay
        for (Thread customerThread : customerThreads) {
            try {
                Thread.sleep(2000); // Introduce a 2-second delay before starting each customer thread
            } catch (InterruptedException e) {
                System.err.println("Error introducing delay: " + e.getMessage());
            }
            customerThread.start();
            Utils.log("New customer [" + customerThread.getName() + "] entered the system", Utils.GREEN);
        }
    }

    public synchronized void stopSimulation() {
        // Stop vendors
        for (Vendor vendor : vendors) {
            vendor.stop();
        }

        // Stop customers
        for (Customer customer : customers) {
            customer.stop();
        }

        // Wait for all threads to finish
        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            Utils.log("Simulation interrupted: " + e.getMessage(), Utils.RED);
        }

        Utils.log("Simulation stopped.");
    }
}
