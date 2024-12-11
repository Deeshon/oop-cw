package com.oop.ticketingSystem.service;

import com.oop.ticketingSystem.entity.ConfigurationEntity;

public interface ConfigurationService {

    ConfigurationEntity saveConfiguration(ConfigurationEntity configurationEntity);

    ConfigurationEntity getById(int id);

    ConfigurationEntity getLastEntry();


}
