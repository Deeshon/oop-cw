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

        // Initialize the simulation
        Simulation simulation = new Simulation();

        // Prompt for the number of vendors and customers
        System.out.println("Enter the number of customers: ");
        int numberOfCustomers = scanner.nextInt();

        System.out.println("Enter the number of vendors: ");
        int numberOfVendors = scanner.nextInt();

        // Start the simulation
        simulation.startSimulation(numberOfVendors, numberOfCustomers, ticketPool, config);

        // Allow the user to stop the simulation
        System.out.println("------------------ Press ENTER to stop ------------------");
        scanner.nextLine();
        scanner.nextLine();

        simulation.stopSimulation();
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
