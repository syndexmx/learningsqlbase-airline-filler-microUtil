package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.controller;

import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.generators.CityGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InitController {

    @Autowired
    CityGenerator cityGenerator;

    @PutMapping(path = "/init")
    public ResponseEntity<String> init() {
        System.out.println("SQL Base generation initiated by Controller...");
        cityGenerator.generateCities();
        return new ResponseEntity<>("Initiated!", HttpStatus.OK);
    }
}
