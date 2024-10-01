package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.services;


import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@PropertySource("cities.properties")
public class CityGeneratorService {

    @Value("#{'${citygenerator.citynames}'.split(',')}")
    private List<String> listCities;

    @Autowired
    CityRepository cityRepository;

    @PostMapping("/initcities")
    public ResponseEntity<String> generateCities() {
        for (String s : listCities) {
            City city = City.builder().name(s).build();
            cityRepository.save(city);
        }
        return new ResponseEntity<String>("Generation", HttpStatus.CREATED);
    }
}
