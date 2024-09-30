package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.generators;


import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.CityRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
@PropertySource("cities.properties")
public class CityGenerator {

    @Value("#{'${citygenerator.citynames}',split(',')}")
    private List<String> listCities;

    public CityGenerator(@Autowired final List<String> listCities,
                         @Autowired final CityRepository cityRepository) {
        this.listCities = listCities;
        this.cityRepository = cityRepository;
    }

    @Autowired
    CityRepository cityRepository;

    public void generateCities() {
        for (String s : listCities) {
            City city = City.builder().name(s).build();
            cityRepository.save(city);
        }
    }
}
