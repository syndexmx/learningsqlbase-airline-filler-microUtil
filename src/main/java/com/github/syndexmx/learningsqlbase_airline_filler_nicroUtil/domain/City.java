package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cities")
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cityId;

    @Column(name = "city_name")
    String name;

    transient Integer population;

    public City(String name) {
        this.name = name;
    }
}
