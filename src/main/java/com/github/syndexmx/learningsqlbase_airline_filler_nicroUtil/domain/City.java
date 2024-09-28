package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    String cityId;

    @Column(name = "city_name")
    String name;

    transient int travellingPopulation;
}
