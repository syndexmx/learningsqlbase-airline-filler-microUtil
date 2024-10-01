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
    Map<String, Integer> mapMaleFirstNameToFrequency;

    List<String> listMaleFirstNames = new ArrayList<String>();

    @Value("#{${person.femalefirstnamesmaptofrequency}}")
    Map<String, Integer> mapFemaleFirstNameToFrequency;

    List<String> listFemaleFirstNames = new ArrayList<String>();

    @Value("${people.malepromille}")
    Integer MALE_PROMILLE;

    void generatePeople(City city) {
        initListLastNameIndexToLastName();
        initListMaleFirstNameIndexToFirstName();
        initListFemaleFirstNameIndexToFirstName();
        for (int i = 0; i < city.getPopulation(); i++) {
            String lastName = listLastNames.get(getRandom(listLastNames.size()));
            String firstName;
            String secondName;
            if (getRandom(1000) < MALE_PROMILLE) {
                firstName = listMaleFirstNames.get(getRandom(listMaleFirstNames.size()));
                secondName = listMaleFirstNames.get(getRandom(listMaleFirstNames.size()));
            } else {
                firstName = listFemaleFirstNames.get(getRandom(listFemaleFirstNames.size()));
                secondName = listFemaleFirstNames.get(getRandom(listFemaleFirstNames.size()));
            }
            Person person = Person.builder()
                    .firstName(firstName)
                    .secondName(secondName)
                    .lastName(lastName)
                    .sex(Person.SexesList.NA)
                    .city(city)
                    .build();
            log.debug("Person: " + person.toString());
            personsRepository.save(person);
        }
    }

    private boolean isMaleFirstNameListInitiated = false;

    private void initListMaleFirstNameIndexToFirstName() {
        if (!isMaleFirstNameListInitiated) {
            for (String s : mapMaleFirstNameToFrequency.keySet()) {
                for (int i = 0; i < mapMaleFirstNameToFrequency.get(s); i++) {
                    listMaleFirstNames.add(s);
                }
            }
        }
        isMaleFirstNameListInitiated = true;
    }

    private boolean isFemaleFirstNameListInitiated = false;

    private void initListFemaleFirstNameIndexToFirstName() {
        if (!isFemaleFirstNameListInitiated) {
            for (String s : mapFemaleFirstNameToFrequency.keySet()) {
                for (int i = 0; i < mapFemaleFirstNameToFrequency.get(s); i++) {
                    listFemaleFirstNames.add(s);
                }
            }
        }
        isFemaleFirstNameListInitiated = true;
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
