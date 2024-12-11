package com.oop.ticketingSystem.impl;

import com.oop.ticketingSystem.entity.ConfigurationEntity;
import com.oop.ticketingSystem.repository.ConfigurationRepository;
import com.oop.ticketingSystem.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public ConfigurationEntity saveConfiguration(ConfigurationEntity configurationEntity) {
        return  configurationRepository.save(configurationEntity);
    }

    @Override
    public ConfigurationEntity getById(int id) {
        return configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Config not found"));
    }

    @Override
    public ConfigurationEntity getLastEntry() {
        return configurationRepository.findTopByOrderByIdDesc();
    }
}
