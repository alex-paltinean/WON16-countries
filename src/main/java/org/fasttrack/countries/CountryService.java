package org.fasttrack.countries;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fasttrack.countries.exception.EntityNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader countryReader;
    private final RestClient restClient;
    private final CountryRepository countryRepository;

    @PostConstruct
    public void init() {
        System.out.println("Post constructor on Country Service");
        countryRepository.saveAll(countryReader.readCountries());
        System.out.println("Service initialized with " + countryRepository.findAll());
    }

    public List<Country> getAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).toList();
    }

    public List<Country> getByContinent(String continent) {
        return countryRepository.findByContinentNative(continent);
    }

    public Optional<Country> getById(long id) {
        return countryRepository.findById(id);
    }

    public Country delete(long id) {
        Country countryToBeDeleted = getById(id).orElseThrow(() -> new EntityNotFoundException("Can't delete missing country", id));
        countryRepository.deleteById(id);
        return countryToBeDeleted;
    }

    public Country add(Country country) {
        country.setId(null);
        countryRepository.save(country);
        return country;
    }

    public Country update(Country country) {
        countryRepository.save(country);
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
