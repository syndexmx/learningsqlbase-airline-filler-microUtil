package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.services;


import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@PropertySource("cities.properties")
@Slf4j
public class CityGeneratorService {

    @Value("#{${citygenerator.cities}}")
    private Map<String, Integer> mapCityToPopulation;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PeopleGeneratorService peopleGeneratorService;

    @PostMapping("/initcities")
    public ResponseEntity<String> generateCities() {
        for (String s : mapCityToPopulation.keySet()) {
            City city = City.builder().name(s).population(mapCityToPopulation.get(s)).build();
            log.debug("City " + s + "creation");
            cityRepository.save(city);
            peopleGeneratorService.generatePeople(city);
        }
        return new ResponseEntity<String>("Generation", HttpStatus.CREATED);
    }
}
