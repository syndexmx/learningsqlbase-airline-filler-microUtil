package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.controller;

import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.services.CityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @Autowired
    CityGeneratorService cityGeneratorService;

    @PutMapping(path = "/init")
    public ResponseEntity<String> init() {
        System.out.println("SQL Base generation initiated by Controller...");
        cityGeneratorService.generateCities();
        return new ResponseEntity<>("Initiated!", HttpStatus.OK);
    }
}
