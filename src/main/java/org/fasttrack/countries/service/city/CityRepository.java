package org.fasttrack.countries.service.city;

import org.fasttrack.countries.model.city.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CityRepository extends CrudRepository<City, Long> {
    List<City> findByCountryId(Long id);
}
