package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.Vendor;
import com.oop.ticketingSystem.repository.VendorRepository;
import com.oop.ticketingSystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;

public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateVendor(Vendor vendor) {
        return vendor;
    }
}
