package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.Customer;
import com.oop.ticketingSystem.repository.CustomerRepository;
import com.oop.ticketingSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() { return customerRepository.findAll(); }
}
