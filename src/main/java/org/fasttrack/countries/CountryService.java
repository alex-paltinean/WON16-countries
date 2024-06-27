package org.fasttrack.countries;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fasttrack.countries.exception.EntityNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader countryReader;
    private final RestClient restClient;
    private final List<Country> countries = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("Post constructor on Country Service");
        countries.addAll(countryReader.readCountries());
        System.out.println("Service initialized with " + countries);
    }

    public List<Country> getAll() {
        return countries;
    }

    public List<Country> getByContinent(String continent) {
        return countries.stream()
                .filter(country -> country.getContinent().equalsIgnoreCase(continent))
                .toList();
    }

    public Optional<Country> getById(long id) {
        return countries.stream()
                .filter(country -> country.getId() == id)
                .findFirst();
    }

    public Country delete(long id) {
        Country countryToBeDeleted = getById(id).orElseThrow(() -> new EntityNotFoundException("Can't delete missing country", id));
        countries.remove(countryToBeDeleted);
        return countryToBeDeleted;
    }

    public Country add(Country country) {
        countries.add(country);
        return country;
    }

    public Country update(Country country) {
        delete(country.getId());
        add(country);
        return country;
    }

    public ExternalCountry getExternalDataById(long id) {
        Country country = getById(id).orElseThrow(() -> new EntityNotFoundException("Can't retrieve external data for missing country", id));
        ResponseEntity<List<ExternalCountry>> response = restClient.get()
                .uri("https://restcountries.com/v3.1/name/" + country.getName() + "?fullText=true")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        return response.getBody().get(0);

    }
}
