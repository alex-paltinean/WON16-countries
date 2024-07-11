package org.fasttrack.countries.model.country;

public record ExternalCountry(ExternalCountryName name, String startOfWeek) {
}

record ExternalCountryName(String common) {

}

