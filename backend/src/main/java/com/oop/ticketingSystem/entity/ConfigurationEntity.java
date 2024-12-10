package com.oop.ticketingSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "configuration")
public class ConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int numberOfVendors;
    private int numberOfCustomers;

    public ConfigurationEntity() {}

    public ConfigurationEntity(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate, int numberOfVendors, int numberOfCustomers) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.numberOfVendors = numberOfVendors;
        this.numberOfCustomers = numberOfCustomers;
    }

    @Override
    public String toString() {
        return String.format("Configuration parameter:- " +
                "Total ticket: %d, " +
                "Max ticket capacity: %d, " +
                "Ticket release rate: %d, " +
                "Ticket retrieval rate: %d" +
                "Number of vendors: %d"     +
                "Number of customers %d",
                totalTickets, maxTicketCapacity, ticketReleaseRate, customerRetrievalRate, numberOfVendors, numberOfCustomers);
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    public void setNumberOfVendors(int numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
