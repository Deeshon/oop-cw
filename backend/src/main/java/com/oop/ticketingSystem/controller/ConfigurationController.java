package com.oop.ticketingSystem.controller;

import com.oop.ticketingSystem.entity.ConfigurationEntity;
import com.oop.ticketingSystem.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    // post api for saving config parameters
    // http://localhost:8080/api/config
    @PostMapping
    public ResponseEntity<ConfigurationEntity> saveConfiguration(@RequestBody ConfigurationEntity config) {
        ConfigurationEntity savedConfig = configurationService.saveConfiguration(config);
        return new ResponseEntity<>(config, HttpStatus.CREATED);
    }
}