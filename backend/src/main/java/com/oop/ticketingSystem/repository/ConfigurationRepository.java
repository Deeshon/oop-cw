package com.oop.ticketingSystem.repository;

import com.oop.ticketingSystem.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {
}
