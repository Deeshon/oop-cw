package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.ConfigurationEntity;
import com.oop.ticketingSystem.service.ConfigurationService;
import com.oop.ticketingSystem.service.SimulationService;
import org.example.Configuration;
import org.example.Simulation;
import org.example.TicketPool;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulationServiceImpl implements SimulationService {

    private final Simulation simulation;
    private Configuration config;

    @Autowired
    private ConfigurationService configurationService;

    public SimulationServiceImpl() {
        this.simulation = new Simulation();
    }

    @Override
    public void startSimulation() {
        TicketPool ticketPool = new TicketPool(100);

        ConfigurationEntity savedConfig = configurationService.getById(1);
        Utils.log(savedConfig.toString(), Utils.GREEN);

        config = new Configuration(savedConfig.getTotalTickets(), savedConfig.getMaxTicketCapacity(), savedConfig.getTicketReleaseRate(), savedConfig.getCustomerRetrievalRate());

        // start the simulation
        simulation.startSimulation(savedConfig.getNumberOfVendors(), savedConfig.getNumberOfCustomers(), ticketPool, config);

        System.out.println("---------------------------------------------" + simulation.getVendors());
    }

    @Override
    public void stopSimulation() {
        // stop the simulation
        simulation.stopSimulation();
    }

    @Override
    public List<String> getVendors() {
        return simulation.getVendors();
    }

    @Override
    public void addVendor() {
        simulation.addVendor(config);
    }

    @Override
    public void discontinueVendor(String vendorId) {
        simulation.discontinueVendor(vendorId);
    }

    @Override
    public List<String> getCustomers() {
        return simulation.getCustomers();
    }

    @Override
    public void addCustomer() {
        simulation.addCustomer(config);
    }

    @Override
    public void discontinueCustomer(String customerId) {
        simulation.removeCustomer(customerId);
    }


}
