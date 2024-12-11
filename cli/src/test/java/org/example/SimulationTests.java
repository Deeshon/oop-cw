package org.example;

import org.example.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTests {
    private Simulation simulation;
    private TicketPool ticketPool;
    private Configuration config;

    @BeforeEach
    void setUp() {
        simulation = new Simulation();
        ticketPool = new TicketPool(100); // Assuming TicketPool has a constructor that takes the total number of tickets.
        config = new Configuration(100, 100, 2, 1); // Assuming Configuration has ticketReleaseRate, totalTickets, and customerRetrievalRate.
    }

    @Test
    void testStartSimulation() {
        simulation.startSimulation(2, 3, ticketPool, config);
        List<String> vendors = simulation.getVendors();
        List<String> customers = simulation.getCustomers();

        assertEquals(2, vendors.size(), "There should be 2 vendors started.");
        assertEquals(3, customers.size(), "There should be 3 customers started.");
    }

    @Test
    void testStopSimulation() {
        simulation.startSimulation(2, 3, ticketPool, config);
        simulation.stopSimulation();

        assertFalse(simulation.isStatus(), "Simulation status should be false");
    }

    @Test
    void testAddVendor() {
        simulation.startSimulation(2, 0, ticketPool, config);
        simulation.addVendor(config);

        List<String> vendors = simulation.getVendors();
        assertEquals(3, vendors.size(), "A new vendor should be added, making the total 3.");
    }

    @Test
    void testDiscontinueVendor() {
        simulation.startSimulation(2, 0, ticketPool, config);
        List<String> vendors = simulation.getVendors();
        String vendorIdToRemove = vendors.get(0);

        simulation.discontinueVendor(vendorIdToRemove);

        assertEquals(1, simulation.getVendors().size(), "One vendor should be removed, leaving 1.");
        assertNull(simulation.getVendorById(vendorIdToRemove), "The removed vendor should not be retrievable.");
    }

    @Test
    void testAddCustomer() {
        simulation.startSimulation(0, 2, ticketPool, config);
        simulation.addCustomer(config);

        List<String> customers = simulation.getCustomers();
        assertEquals(3, customers.size(), "A new customer should be added, making the total 3.");
    }

    @Test
    void testRemoveCustomer() {
        simulation.startSimulation(0, 2, ticketPool, config);
        List<String> customers = simulation.getCustomers();
        String customerIdToRemove = customers.get(0);

        simulation.removeCustomer(customerIdToRemove);

        assertEquals(1, simulation.getCustomers().size(), "One customer should be removed, leaving 1.");
        assertNull(simulation.getCustomerById(customerIdToRemove), "The removed customer should not be retrievable.");
    }

    @Test
    void testGetVendorById() {
        simulation.startSimulation(2, 0, ticketPool, config);
        List<String> vendors = simulation.getVendors();

        String vendorId = vendors.get(0);
        Vendor vendor = simulation.getVendorById(vendorId);

        assertNotNull(vendor, "Vendor with the given ID should be found.");
        assertEquals(vendorId, vendor.getVendorId(), "The retrieved vendor ID should match the expected.");
    }

    @Test
    void testGetCustomerById() {
        simulation.startSimulation(0, 2, ticketPool, config);
        List<String> customers = simulation.getCustomers();

        String customerId = customers.get(0);
        Customer customer = simulation.getCustomerById(customerId);

        assertNotNull(customer, "Customer with the given ID should be found.");
        assertEquals(customerId, customer.getCustomerId(), "The retrieved customer ID should match the expected.");
    }
}
