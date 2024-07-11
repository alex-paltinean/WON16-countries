package org.fasttrack.countries.model.city;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fasttrack.countries.model.country.Country;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "capital")
    private Country capitalOf;

    @JsonIgnore
    @ManyToOne
    private Country country;

    public City(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
