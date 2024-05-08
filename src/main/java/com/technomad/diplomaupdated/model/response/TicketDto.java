package com.technomad.diplomaupdated.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.TicketState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@AllArgsConstructor
//@Builder
//@Accessors(fluent = true)
//@Getter
public class TicketDto {

    private Long id;
    private AppUser ticketOwner;
    private Integer place;
    private UUID uuid;
    private TicketState ticketState;
    @JsonIgnoreProperties({"driver", "routeStations"})
    private Route forRoute;
    @JsonIgnoreProperties({"masterRoute"})
    private Stop fromStop;
    @JsonIgnoreProperties({"masterRoute"})
    private Stop toStop;
}
