package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.TicketSale;
import com.oop.ticketingSystem.repository.TicketSaleRepository;
import com.oop.ticketingSystem.service.TicketSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketSaleServiceImpl implements TicketSaleService {

    @Autowired
    private TicketSaleRepository ticketSaleRepository;

    public TicketSale saveTicketSale(TicketSale sale) {
       return ticketSaleRepository.save(sale);
    }

    public List<TicketSale> getAllTicketSales() {
        return ticketSaleRepository.findAll();
    }
}
