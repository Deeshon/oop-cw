package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.ConfigurationEntity;
import com.oop.ticketingSystem.repository.ConfigurationRepository;
import com.oop.ticketingSystem.service.ConfigurationService;
import com.oop.ticketingSystem.service.SimulationService;
import jdk.jshell.execution.Util;
import org.example.Configuration;
import org.example.Simulation;
import org.example.TicketPool;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulationServiceImpl implements SimulationService {

    private final Simulation simulation;

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

        Configuration config = new Configuration(savedConfig.getTotalTickets(), savedConfig.getMaxTicketCapacity(), savedConfig.getTicketReleaseRate(), savedConfig.getCustomerRetrievalRate());

        // start the simulation
        simulation.startSimulation(savedConfig.getNumberOfVendors(), savedConfig.getNumberOfCustomers(), ticketPool, config);
    }

    @Override
    public void stopSimulation() {
        // stop the simulation
        simulation.stopSimulation();
    }
}
