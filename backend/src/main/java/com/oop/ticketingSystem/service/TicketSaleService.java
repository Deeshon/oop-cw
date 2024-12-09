package com.oop.ticketingSystem.service;

import com.oop.ticketingSystem.entity.TicketSale;

import java.util.List;

public interface TicketSaleService {

    TicketSale saveTicketSale(TicketSale ticketSale);

    List<TicketSale> getAllTicketSales();
}
