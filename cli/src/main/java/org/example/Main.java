package org.example;

import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to your Ticketing System!");

        // Attempt to load configuration
        Configuration config = Configuration.loadFromFile("config.json");

        if (config == null) {
            System.out.println("No configuration file found.");
            config = promptForConfiguration(scanner);
        } else {
            System.out.println("Configuration file found.");
            System.out.println("1. Load saved configuration");
            System.out.println("2. Create new configuration");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Saved configuration loaded.");
                    break;
                case 2:
                    config = promptForConfiguration(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Using saved configuration.");
            }
        }

        System.out.println("Final Configuration:");
        System.out.println(config);

        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

//        // Start Vendor and Customer threads
//        Vendor vendor = new Vendor("PrimeVendors", ticketPool, config.getTicketReleaseRate(), config.getTotalTickets());
//        Thread vendorThread = new Thread(vendor, "PrimeVendors");

        // Create vendor threads
        List<Vendor> vendors = new ArrayList<>();
        List<Thread> vendorThreads = new ArrayList<>();
        int numberOfVendors = 2;
        for (int i = 1; i <=numberOfVendors; i++) {
            Vendor vendor = new Vendor("Vendor"+i, ticketPool, config.getTicketReleaseRate(), config.getTotalTickets());
            vendors.add(vendor);
            vendorThreads.add(new Thread(vendor, "Vendor"+i));
        }

        // Create customer threads
        List<Customer> customers = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();
        int numberOfCustomers = 3;
        for (int i = 1; i <= numberOfCustomers; i++) {
            Customer customer = new Customer(ticketPool, "Customer"+i, "customer"+ Utils.generateId(), config.getCustomerRetrievalRate());
            customers.add(customer);
            customerThreads.add(new Thread(customer, "Customer"+i));

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
            Utils.log("New vendor ["+vendorThread.getName()+"] entered the system.", Utils.GREEN);
        }

        try {
            // Wait for 2 seconds before starting customer
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
            Utils.log("New customer ["+customerThread.getName()+"] entered the system", Utils.GREEN);
        }

        // Allow user to stop simulation
        System.out.println("Press ENTER to stop...");
        scanner.nextLine();

        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);

        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Simulation interrupted!");
        }

        System.out.println("Simulation stopped.");
    }

    private static Configuration promptForConfiguration(Scanner scanner) {
        System.out.println("Enter configuration parameters.");

        System.out.print("Max Ticket Capacity: ");
        int maxTicketCapacity = InputValidator.isPositive(scanner, "Max Ticket Capacity");

        System.out.print("Event Capacity (total number of tickets): ");
        int eventCapacity = InputValidator.isPositive(scanner, "Event Capacity");

        System.out.print("Ticket Release Rate (tickets/sec): ");
        int ticketReleaseRate = InputValidator.isPositive(scanner, "Ticket Release Rate");

        System.out.print("Customer Retrieval Rate (tickets/sec): ");
        int customerRetrievalRate = InputValidator.isPositive(scanner, "Customer Retrieval Rate");

        Configuration config = new Configuration(eventCapacity, maxTicketCapacity, ticketReleaseRate, customerRetrievalRate);

        System.out.println("\nConfiguration complete:");

        System.out.print("Save this configuration for later (y/n)? ");
        String response = InputValidator.isNotEmpty(scanner, "Response").toLowerCase();

        if (response.equals("y")) {
            config.saveToFile("config.json");
        }

        return config;
    }
}
