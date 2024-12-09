package com.oop.ticketingSystem.repository;

import com.oop.ticketingSystem.entity.TicketSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSaleRepository extends JpaRepository<TicketSale, Long> {
}