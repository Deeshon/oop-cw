package com.oop.ticketingSystem.controller;

import com.oop.ticketingSystem.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/simulation")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    // api for starting the simulation
    // http://localhost:8080/api/simulation/start
    @GetMapping("/start")
    public String startSimulation() {
        simulationService.startSimulation();
        return "Simulation Started";
    }

    // api for stopping the simulation
    // http://localhost:8080/api/simulation/stop
    @GetMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped";
    }
}
