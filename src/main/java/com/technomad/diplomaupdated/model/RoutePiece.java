package com.technomad.diplomaupdated.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RoutePiece {

    @Id
    @SequenceGenerator(
            name = "routePiece_sequence",
            sequenceName = "routePiece_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "routePiece_sequence"
    )
    private Long id;
    @ManyToOne
    private Route masterRoute;

    @OneToOne
    @JsonIgnoreProperties({"masterRoute", "arrivalTime", "departureTime", "cost", "state"})
    private Stop startPoint;
    @OneToOne
    @JsonIgnoreProperties({"masterRoute", "arrivalTime", "departureTime", "cost", "state"})
    private Stop endPoint;

    @OneToMany(mappedBy = "masterRoutePiece")
    List<TicketCodeHolder> bookings = new ArrayList<>();

    public RoutePiece(Stop startPoint, Stop endPoint, Route masterRoute) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.masterRoute = masterRoute;
    }

    public Boolean isPlaceFree(Integer place) {

        for (TicketCodeHolder holder : bookings
             ) {
            if (holder.getPlace().equals(place)) {
                return false;
            }
        }
        return true;
    }
}
