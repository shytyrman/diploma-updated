package com.technomad.diplomaupdated.model;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.exception.IllegalRequestException;
import com.technomad.diplomaupdated.model.state.RouteState;
import com.technomad.diplomaupdated.model.state.TicketState;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Ticket {


    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long id;

    @ManyToOne
    private AppUser ticketOwner;
    @ManyToOne
    private Route forRoute;
    @ManyToOne
    private Stop fromStop;
    @ManyToOne
    private Stop toStop;
    private Integer place;

    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private TicketState ticketState;

    public Ticket(AppUser ticketOwner, Route forRoute, Stop fromStop, Stop toStop, Integer place) {
        this.ticketOwner = ticketOwner;
        if (!forRoute.getRouteState().equals(RouteState.AVAILABLE)) {
            throw new IllegalRequestException("This Route is already done!");
        }
        this.forRoute = forRoute;
        if (!forRoute.hasStop(fromStop) && !forRoute.hasStop(toStop)) {
            throw new IllegalRequestException("Stops are not from this Route!");
        }
        this.fromStop = fromStop;
        this.toStop = toStop;
        if (!forRoute.isPlaceFreeInRouteBetweenStops(place, fromStop, toStop)) {
            throw new IllegalRequestException("The given place between Stops: " + fromStop + " - " + toStop + ". isn't free.");
        }
        this.place = place;
        this.uuid = UUID.randomUUID();
        this.ticketState = TicketState.NOT_CHECKED;
    }
}
