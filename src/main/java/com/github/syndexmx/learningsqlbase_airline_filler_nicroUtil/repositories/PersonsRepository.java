package com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.repositories;

import com.github.syndexmx.learningsqlbase_airline_filler_nicroUtil.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Long> {
}
