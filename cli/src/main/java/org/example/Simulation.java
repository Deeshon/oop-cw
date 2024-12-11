package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.utils.Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private TicketPool ticketPool;
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private boolean status = false;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public synchronized void startSimulation(int numberOfVendors, int numberOfCustomers, TicketPool ticketPool, Configuration config) {

        // Initialize the ticket pool
        this.ticketPool = ticketPool;

        // Clear the existing vendor/customer lists to restart the simulation
        vendors.clear();
        vendorThreads.clear();
        customers.clear();
        customerThreads.clear();

        // Create vendor threads
        for (int i = 1; i <= numberOfVendors; i++) {
            Vendor vendor = new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets());
            vendors.add(vendor);
            vendorThreads.add(new Thread(vendor, vendor.getVendorId()));
        }

        // Create customer threads
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
            customers.add(customer);
            customerThreads.add(new Thread(customer, customer.getCustomerId()));
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

        this.status = true;
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

        vendors.clear();
        vendorThreads.clear();
        customers.clear();
        customerThreads.clear();

        clearTicketSales();

        this.status = false;
        Utils.log("Simulation stopped.");
    }

    public synchronized void restartSimulation(int numberOfVendors, int numberOfCustomers, TicketPool ticketPool, Configuration config) {
        Utils.log("Restarting simulation...", Utils.YELLOW);

        // Stop the current simulation to clear existing threads and data
        stopSimulation();

        // Start a new simulation with the provided parameters
        startSimulation(numberOfVendors, numberOfCustomers, ticketPool, config);

        Utils.log("Simulation restarted successfully.", Utils.GREEN);
    }


    public synchronized void  addVendor(Configuration config) {
        Vendor vendor = new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets());
        vendors.add(vendor);
        Thread vendorThread = new Thread(vendor, vendor.getVendorId());
        vendorThreads.add(vendorThread);
        vendorThread.start();
        Utils.log("New vendor [" + vendor.getVendorId() + "] added to the simulation.", Utils.GREEN);
    }

    public synchronized void discontinueVendor(String vendorId) {
        Vendor vendorToRemove = getVendorById(vendorId);
        if (vendorToRemove != null) {
            vendorToRemove.stop();
            vendors.remove(vendorToRemove);
            vendorThreads.removeIf(thread -> thread.getName().equals(vendorId));
            Utils.log("Vendor [" + vendorId + "] removed from the simulation.", Utils.RED);
        }
    }

    public synchronized void addCustomer(Configuration config) {
        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
        customers.add(customer);
        Thread customerThread = new Thread(customer, customer.getCustomerId());
        customerThreads.add(customerThread);
        customerThread.start();
        Utils.log("New customer [" + customer.getCustomerId() + "] added to the simulation.", Utils.GREEN);
    }

    public synchronized void removeCustomer(String customerId) {
        Customer customerToRemove = getCustomerById(customerId);
        if (customerToRemove != null) {
            customerToRemove.stop();
            customers.remove(customerToRemove);
            customerThreads.removeIf(thread -> thread.getName().equals(customerId));
            Utils.log("Customer [" + customerId + "] removed from the simulation.", Utils.RED);
        }
    }

    public Vendor getVendorById(String vendorId) {
        return vendors.stream().filter(v -> v.getVendorId().equals(vendorId)).findFirst().orElse(null);
    }

    public Customer getCustomerById(String customerId) {
        return customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst().orElse(null);
    }

    public List<String> getVendors() {
        List<String> vendorIds = new ArrayList<>();
        for (Vendor vendor : vendors) {
            vendorIds.add(vendor.getVendorId());
        }
        return vendorIds;
    }

    public List<String> getCustomers() {
        List<String> customerIds = new ArrayList<>();
        for (Customer customer : customers) {
            customerIds.add(customer.getCustomerId());
        }
        return customerIds;
    }

    public void clearTicketSales() {
        new Thread(() -> {
            try {
                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/sales/clear"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .exceptionally(error -> {
                            return null;
                        });
            } catch (Exception e) {
                Utils.log("Error clearing sales: " + e.getMessage(), Utils.RED);
            }
        }).start();
    }

    public boolean isStatus() {
        return status;
    }
}
