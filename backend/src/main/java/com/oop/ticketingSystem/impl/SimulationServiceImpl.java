package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.service.SimulationService;
import org.example.Configuration;
import org.example.Simulation;
import org.example.TicketPool;
import org.springframework.stereotype.Service;

@Service
public class SimulationServiceImpl implements SimulationService {

    private final Simulation simulation;

    public SimulationServiceImpl() {
        this.simulation = new Simulation();
    }

    @Override
    public void startSimulation() {
        TicketPool ticketPool = new TicketPool(100);
        Configuration config = new Configuration(100, 50, 1, 2);

        // start the simulation
        simulation.startSimulation(2, 3, ticketPool, config);
    }

    @Override
    public void stopSimulation() {
        // stop the simulation
        simulation.stopSimulation();
    }
}
