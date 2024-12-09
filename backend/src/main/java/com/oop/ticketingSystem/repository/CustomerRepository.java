package com.oop.ticketingSystem.repository;

import com.oop.ticketingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
