package org.fasttrack.countries.service.city;

import lombok.RequiredArgsConstructor;
import org.fasttrack.countries.model.city.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City save(City city){
        return cityRepository.save(city);
    }

    public List<City> findByCountryId(Long id){
        return cityRepository.findByCountryId(id);
    }

}
