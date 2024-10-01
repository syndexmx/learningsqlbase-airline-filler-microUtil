package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "people")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long personId;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "second_name")
    String secondName;

    @Column(name = "last_name")
    String lastName;

    public enum SexesList {
        NA,
        MALE,
        FEMALE
    }

    @Column(name = "sex")
    SexesList sex;

    @ManyToOne
    @JoinColumn(name = "city_id")
    City city;

    @Column(name = "birth_date")
    LocalDate birthDate;


}
