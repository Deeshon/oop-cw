package com.oop.ticketingSystem.service;

import com.oop.ticketingSystem.entity.Vendor;

public interface VendorService {

    Vendor createVendor(Vendor vendor);

    Vendor updateVendor(Vendor vendor);
}
