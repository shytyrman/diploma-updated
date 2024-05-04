package com.technomad.diplomaupdated.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
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

    @Column(nullable = false)
    private String name;

    public Station(String name) {
        this.name = name;
    }
}
