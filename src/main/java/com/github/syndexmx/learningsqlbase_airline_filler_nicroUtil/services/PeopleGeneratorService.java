package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.services;

import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.Person;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.PersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@PropertySource("people.properties")
@Slf4j(topic = "People generation.")
public class PeopleGeneratorService {

    @Autowired
    PersonsRepository personsRepository;

    @Value("#{${person.maplastnamestofrequency}}")
    Map<String, Integer> mapLastnameToFrequency;

    List<String> listLastNames = new ArrayList<String>();

    @Value("#{${person.malefirstnamesmaptofrequency}}")
    Map<String, Integer> mapFirstNameToFrequency;

    List<String> listFirstNames = new ArrayList<String>();

    void generatePeople(City city) {
        initListLastNameIndexToLastName();
        initListFirstNameIndexToFirstName();
        for (int i = 0; i < city.getPopulation(); i++) {
            Person person = Person.builder()
                    .firstName(listFirstNames.get(getRandom(listFirstNames.size())))
                    .secondName(listFirstNames.get(getRandom(listFirstNames.size())))
                    .lastName(listLastNames.get(getRandom(listLastNames.size())))
                    .sex(Person.SexesList.NA)
                    .city(city)
                    .build();
            log.debug("Person: " + person.toString());
            personsRepository.save(person);
        }
    }

    private boolean isFirstNameListInitiated = false;

    private void initListFirstNameIndexToFirstName() {
        if (!isFirstNameListInitiated) {
            for (String s : mapFirstNameToFrequency.keySet()) {
                for (int i = 0; i < mapFirstNameToFrequency.get(s); i++) {
                    listFirstNames.add(s);
                }
            }
        }
        isFirstNameListInitiated = true;
    }

    private boolean isLastNameListInitiated = false;

    private void initListLastNameIndexToLastName() {
        if (!isLastNameListInitiated) {
            for (String s : mapLastnameToFrequency.keySet()) {
                for (int i = 0; i < mapLastnameToFrequency.get(s); i++) {
                    listLastNames.add(s);
                }
            }
        }
        isLastNameListInitiated = true;
    }

    private int getRandom(Integer lowerThan) {
        Random random = new Random();
        return random.nextInt(lowerThan);
    }

}
