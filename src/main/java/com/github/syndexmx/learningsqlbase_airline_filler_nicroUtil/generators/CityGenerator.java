package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.generators;


import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@PropertySource("classpath:/src/main/resources/cities.properties")
public class CityGenerator {

    @Value("${city.names}")
    final private List<String> citiesList;

    @Autowired
    CityRepository cityRepository;

    public CityGenerator(List<String> citiesList) {
        this.citiesList = citiesList;
    }

    public void generateCities() {
        for (String s : citiesList) {
            City city = City.builder().name(s).build();
            cityRepository.save(city);
        }
    }
}
