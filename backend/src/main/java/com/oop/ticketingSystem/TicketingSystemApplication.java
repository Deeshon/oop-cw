package com.oop.ticketingSystem;

import com.oop.ticketingSystem.entity.Customer;
import com.oop.ticketingSystem.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingSystemApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	public TicketingSystemApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TicketingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		customerRepository.save(new Customer("John Doe"));
		customerRepository.save(new Customer("Jane Doe"));
		customerRepository.save(new Customer("Alice Sander"));
	}


}
