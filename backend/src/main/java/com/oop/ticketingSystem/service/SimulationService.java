package com.oop.ticketingSystem.service;

import java.util.List;

public interface SimulationService {

    void startSimulation();

    void stopSimulation();

    List<String> getVendors();

    void addVendor();

    void discontinueVendor(String vendorId);

    List<String> getCustomers();

    void addCustomer();

    void discontinueCustomer(String customerId);
}
