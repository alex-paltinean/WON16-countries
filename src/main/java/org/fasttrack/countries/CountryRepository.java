package org.fasttrack.countries;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findByContinent(String continent);

    @Query(value = "select c from Country c where continent = ?1")
    List<Country> findByContinentJPQL(String continent);

    @Query(value = "select * from country where country_continent = ?1", nativeQuery = true)
    List<Country> findByContinentNative(String continent);
}
