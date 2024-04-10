package com.technomad.diplomaupdated.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.state.RouteState;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Route {

    @Id
    @SequenceGenerator(
            name = "route_sequence",
            sequenceName = "route_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "route_sequence"
    )
    private Long id;

    private RouteState routeState;
    @ManyToOne
    private AppUser driver;
    private String description;


    @OneToMany(mappedBy = "masterRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("masterRoute")
////    @JoinColumn(name = "route_id")
    private List<Stop> routeStations = new ArrayList<>();

    @OneToMany(mappedBy = "masterRoute",  cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("masterRoute")
    private List<RoutePiece> routePieces = new ArrayList<>();

    public Route(String description) {
        this.description = description;
    }

    public Boolean hasStop(Stop stop) {

        if (routeStations.contains(stop)) {
            return true;
        }
        return false;
    }

    public Boolean hasStopId(Long id) {

        for (Stop stop : routeStations
             ) {
            if (stop.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isPlaceFreeInRouteBetweenStops(Integer place, Stop start, Stop finish) {

        boolean active = false;

        for (RoutePiece routePiece : routePieces
             ) {
            if (routePiece.getStartPoint().equals(start)) {
                active = true;
            }
            if (!active && routePiece.isPlaceFree(place)) {
                return false;
            }
            if (routePiece.getEndPoint().equals(finish)) {
                break;
            }
        }
        return true;
    }
}
