package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @PutMapping(path = "/init")
    public ResponseEntity<String> init() {
        System.out.println("SQL Base generation initiated by Controller...");

        return new ResponseEntity<>("Initiated!", HttpStatus.OK);
    }
}
