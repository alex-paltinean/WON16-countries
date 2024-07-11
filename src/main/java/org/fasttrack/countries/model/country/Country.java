package org.fasttrack.countries.model.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.fasttrack.countries.model.city.City;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@Entity
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private City capital;
    @Column
    private long population;
    @Column
    private long area;
    @Column(length = 15, name = "country_continent")
    private String continent;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Country> neighbours;


//    @OneToMany(mappedBy = "country")
//    private List<City> cities;
}
