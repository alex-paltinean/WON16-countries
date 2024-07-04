package org.fasttrack.countries;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(columnDefinition = "varchar(20) default null")
    private String capital;
    @Column
    private long population;
    @Column
    private long area;
    @Column(length = 15, name = "country_continent")
    private String continent;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> neighbours;
}
