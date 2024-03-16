package com.technomad.diplomaupdated.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Stop {

    @Id
    @SequenceGenerator(
            name = "stop_sequence",
            sequenceName = "stop_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stop_sequence"
    )
    private Long id;

    @ManyToOne
    private Station station;
    @ManyToOne
    @JoinColumn(name = "route-id")
    private Route masterRoute;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private BigDecimal cost;

    public Stop(Station station, Route masterRoute, LocalDateTime arrivalTime, LocalDateTime departureTime, BigDecimal cost) {
        this.station = station;
        this.masterRoute = masterRoute;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.cost = cost;
    }
}
