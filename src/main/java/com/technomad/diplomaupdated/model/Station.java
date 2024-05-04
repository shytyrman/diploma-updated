package com.technomad.diplomaupdated.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
//@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueNameConstraint", columnNames = {"name"})})
public class Station {

    @Id
    @SequenceGenerator(
            name = "station_sequence",
            sequenceName = "station_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "station_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Station(String name) {
        this.name = name;
    }
}
