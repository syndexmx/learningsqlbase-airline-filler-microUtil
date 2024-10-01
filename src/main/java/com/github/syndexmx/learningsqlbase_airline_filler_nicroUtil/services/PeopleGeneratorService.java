package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.services;

import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.City;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.Person;
import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories.PersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@PropertySource("people.properties")
@Slf4j(topic = "People generation.")
public class PeopleGeneratorService {

    @Autowired
    PersonsRepository personsRepository;

    @Value("#{${person.maplastnamestofrequency}}")
    Map<String, Integer> mapLastnameToFrequency;

    List<String> listLastNames = new ArrayList<String>();

    @Value("${people.malepromille}")
    Integer MALE_PROMILLE;

    @Value("${lastyear}")
    Integer lastYear;

    void generatePeople(City city) {
        initListLastNameIndexToLastName();
        initListMaleFirstNameIndexToFirstName();
        initListFemaleFirstNameIndexToFirstName();
        initListMaleAgePyramid();
        initListFemaleAgePyramid();
        for (int i = 0; i < city.getPopulation(); i++) {
            String lastName = listLastNames.get(getRandom(listLastNames.size()));
            String firstName;
            String secondName;
            Person.SexesList sex;
            int age;
            if (getRandom(1000) < MALE_PROMILLE) {
                firstName = listMaleFirstNames.get(getRandom(listMaleFirstNames.size()));
                secondName = listMaleFirstNames.get(getRandom(listMaleFirstNames.size()));
                sex = Person.SexesList.MALE;
                age = listMaleAgeContent.get(getRandom(listMaleAgeContent.size()))
                        + getRandom(5);
            } else {
                firstName = listFemaleFirstNames.get(getRandom(listFemaleFirstNames.size()));
                secondName = listFemaleFirstNames.get(getRandom(listFemaleFirstNames.size()));
                sex = Person.SexesList.FEMALE;
                age = listFemaleAgeContent.get(getRandom(listFemaleAgeContent.size()))
                        + getRandom(5);
            }
            String birthDateString = getBirthDayString(age);
            LocalDate birthDate = LocalDate.parse(birthDateString);
            Person person = Person.builder()
                    .firstName(firstName)
                    .secondName(secondName)
                    .lastName(lastName)
                    .sex(sex)
                    .birthDate(birthDate)
                    .city(city)
                    .build();
            log.debug("Person: " + person.toString());
            personsRepository.save(person);
        }
    }

    private String getBirthDayString(int age) {
        int year = lastYear - age;
        Integer birthYear = Integer.valueOf(year);
        String birthYearString = birthYear.toString();
        String birthDateString = Strings.concat(birthYearString, "-");
        int month = getRandom(12) + 1;
        Integer birthMonth = Integer.valueOf(month);
        String birthMonthString = birthMonth.toString();
        if (birthMonthString.length() < 2) {
            birthMonthString = '0' + birthMonthString;
        }
        birthDateString = Strings.concat(birthDateString, birthMonthString);
        birthDateString = Strings.concat(birthDateString, "-");
        int day;
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day = getRandom(31);
            case 2 -> day = getRandom(28);
            default ->  day = getRandom(30);
        }
        if (year % 4 == 0 && month == 2) {
            day = getRandom(29);
        }
        day = day + 1;
        Integer birthDay = Integer.valueOf(day);
        String birthDayString = birthDay.toString();
        if (birthDayString.length() < 2) {
            birthDayString = '0' + birthDayString;
        }
        birthDateString = Strings.concat(birthDateString, birthDayString);
        return birthDateString;
    }

    @Value("#{${person.malefirstnamesmaptofrequency}}")
    Map<String, Integer> mapMaleFirstNameToFrequency;

    List<String> listMaleFirstNames = new ArrayList<String>();

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

    @Value("#{${person.femalefirstnamesmaptofrequency}}")
    Map<String, Integer> mapFemaleFirstNameToFrequency;

    List<String> listFemaleFirstNames = new ArrayList<String>();

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

    @Value("#{${person.maleagepyramid}}")
    Map<Integer, Integer> mapMaleAgeToFrequency;

    List<Integer> listMaleAgeContent = new ArrayList<Integer>();

    private boolean isMaleAgeListInitiated = false;

    private void initListMaleAgePyramid() {
        if (!isMaleAgeListInitiated) {
            for (Integer integer : mapMaleAgeToFrequency.keySet()) {
                for (int i = 0; i < mapMaleAgeToFrequency.get(integer); i++) {
                    listMaleAgeContent.add(integer);
                }
            }
        }
        isMaleAgeListInitiated = true;
    }

    @Value("#{${person.femaleagepyramid}}")
    Map<Integer, Integer> mapFemaleAgeToFrequency;

    List<Integer> listFemaleAgeContent = new ArrayList<Integer>();

    private boolean isFemaleAgeListInitiated = false;

    private void initListFemaleAgePyramid() {
        if (!isFemaleAgeListInitiated) {
            for (Integer integer : mapFemaleAgeToFrequency.keySet()) {
                for (int i = 0; i < mapFemaleAgeToFrequency.get(integer); i++) {
                    listFemaleAgeContent.add(integer);
                }
            }
        }
        isFemaleAgeListInitiated = true;
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
