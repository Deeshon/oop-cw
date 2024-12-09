package com.oop.ticketingSystem.repository;

import com.oop.ticketingSystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
