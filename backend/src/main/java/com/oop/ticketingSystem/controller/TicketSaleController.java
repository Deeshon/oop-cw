package com.oop.ticketingSystem.controller;

import com.oop.ticketingSystem.entity.TicketSale;
import com.oop.ticketingSystem.service.TicketSaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sales")
public class TicketSaleController {

    @Autowired
    private TicketSaleService ticketSaleService;

    @PostMapping
    public ResponseEntity<TicketSale> recordSale(@RequestBody TicketSale sale) {
        TicketSale savedSale = ticketSaleService.saveTicketSale(sale);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketSale>> getAllSales() {
        List<TicketSale> saleList = ticketSaleService.getAllTicketSales();
        return new ResponseEntity<>(saleList, HttpStatus.OK);
    }
}
