package com.oop.ticketingSystem.controller;

import com.oop.ticketingSystem.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/vendor/all")
    public ResponseEntity<List<String>> getVendors() {
        List<String> vendors = simulationService.getVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/vendor/add")
    public String addVendor() {
        simulationService.addVendor();
        return "Vendor added";
    }

    @PostMapping("/vendor/remove")
    public String discontinueVendor(@RequestBody Map<String, String> requestBody) {
        String vendorId = requestBody.get("vendorId");
        if (vendorId == null || vendorId.isEmpty()) {
            throw new IllegalArgumentException("Vendor ID is required");
        }
        simulationService.discontinueVendor(vendorId);
        return "Vendor removed";
    }

    @GetMapping("/customers/all")
    public ResponseEntity<List<String>> getCustomers() {
        List<String> customers = simulationService.getCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customers/add")
    public String addCustomer() {
        simulationService.addCustomer();
        return "Customer Added";
    }

    @PostMapping("/customers/discontinue")
    public String discontinueCustomer(@RequestBody Map<String, String> requestBody) {
        String customerId = requestBody.get("customerId");
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        simulationService.discontinueCustomer(customerId);
        return "Customer removed";
    }
}
