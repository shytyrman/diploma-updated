package com.technomad.diplomaupdated.service.ticket;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.RoutePiece;
import com.technomad.diplomaupdated.model.TicketCodeHolder;
import com.technomad.diplomaupdated.repository.RouteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Qualifier(value = "Basic")
@AllArgsConstructor
public class BasicTicketCheckService implements TicketCheckService{

    private RouteRepository routeRepository;
    @Override
    public Boolean check(UUID uuid, Long routeId) {

        Route currentRoute = routeRepository.getReferenceById(routeId);
        List<RoutePiece> currentRoutePieceList = routeRepository.getReferenceById(routeId).getRoutePieces();

        for (RoutePiece currentRoutePiece : currentRoutePieceList
             ) {
            List<TicketCodeHolder> currentTicketCodeHolderList = currentRoutePiece.getBookings();
            for (TicketCodeHolder currentTicketCodeHolder : currentTicketCodeHolderList
                 ) {
                if (uuid.equals(currentTicketCodeHolder.getUuid())) {
                    return true;
                }
            }
        }
        return false;
    }
}
