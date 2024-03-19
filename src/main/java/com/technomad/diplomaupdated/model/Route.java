package com.technomad.diplomaupdated.model;

import com.technomad.diplomaupdated.appuser.AppUser;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.angus.mail.iap.ByteArray;

import java.util.ArrayList;

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
    @OneToMany(mappedBy = "masterRoute", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "route_id")
    private ArrayList<Stop> routeStations = new ArrayList<>();
    @ManyToOne
    private AppUser driver;
    private String description;

    public Route(String description, ArrayList<Stop> routeStations) {
        this.routeStations = routeStations;
        this.description = description;
    }
}
