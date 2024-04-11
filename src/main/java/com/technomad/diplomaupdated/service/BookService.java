package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.additional.RoutePiecesComparator;
import com.technomad.diplomaupdated.additional.StopsComparatorByOrder;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.RoutePiece;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.TicketCodeHolder;
import com.technomad.diplomaupdated.model.response.Place;
import com.technomad.diplomaupdated.model.state.PlaceState;
import com.technomad.diplomaupdated.repository.TicketCodeHolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {

    private final RoutePiecesComparator routePiecesComparator;
    private final TicketCodeHolderRepository ticketCodeHolderRepository;

    public List<Place> getReservedPlaces(Route route, Stop start, Stop finish) {

        List<Place> result = new ArrayList<>();

        //Initializing all places
        for (int i = 0; i < 40; i++) {
            if (route.isPlaceFreeInRouteBetweenStops(i, start, finish)) {
                result.add(new Place(i, PlaceState.FREE));
            }
            else {
                result.add(new Place(i, PlaceState.TAKEN));
            }
        }

        return result;
    }

    public void reserve(Route route, Stop start, Stop finish, Integer place, UUID uuid) {

        route.getRoutePieces().sort(routePiecesComparator);
        Boolean active = false;

        for (RoutePiece routePiece : route.getRoutePieces()
             ) {
            if (routePiece.getStartPoint().equals(start)) {
                active = true;
            }
            if (active) {
                TicketCodeHolder ticketCodeHolder = new TicketCodeHolder();
                ticketCodeHolder.setMasterRoutePiece(routePiece);
                ticketCodeHolder.setPlace(place);
                ticketCodeHolder.setUuid(uuid);

                ticketCodeHolderRepository.save(ticketCodeHolder);
            }
            if (routePiece.getEndPoint().equals(finish)) {
                break;
            }
        }
    }

}
