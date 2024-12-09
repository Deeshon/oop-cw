package org.example;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public Configuration() {}

    public Configuration(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getTotalTickets() { return totalTickets; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }

    @Override
    public String toString() {
        return "Total Tickets: " + totalTickets +
                ", Max Ticket Capacity: " + maxTicketCapacity +
                ", Ticket Release Rate: " + ticketReleaseRate +
                ", Customer Retrieval Rate: " + customerRetrievalRate;
    }

    public void saveToFile(String fileName) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved to " + fileName );

        } catch (IOException e) {
            System.err.println("Failed to save configuration " + e.getMessage());
        }
    }

    public static Configuration loadFromFile(String fileName) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }
}
