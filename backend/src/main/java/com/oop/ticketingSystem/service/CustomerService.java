package com.oop.ticketingSystem.service;

import com.oop.ticketingSystem.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();
}
