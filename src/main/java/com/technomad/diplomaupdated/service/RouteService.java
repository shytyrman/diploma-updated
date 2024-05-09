package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.additional.StopsComparatorByOrder;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.RoutePiece;
import com.technomad.diplomaupdated.model.state.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.StopState;
import com.technomad.diplomaupdated.repository.RoutePieceRepository;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StationRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import com.technomad.diplomaupdated.model.request.CreateRouteRequest;
import com.technomad.diplomaupdated.model.request.CreateRouteStopRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class RouteService {

    private final RouteRepository repository;
    private final StopRepository stopRepository;
    private final StationRepository stationRepository;
    private final RoutePieceRepository routePieceRepository;
    private final StopsComparatorByOrder stopsComparatorByOrder;

    @Transactional
    public Route addRoute(CreateRouteRequest request, AppUser appUser) {

        ArrayList<CreateRouteStopRequest> stopRequests = request.stops();
        Iterator<CreateRouteStopRequest> iterator = stopRequests.iterator();
        Route route = new Route();
        route.setDriver(appUser);
        int order = 0;

        repository.save(route);
        while (iterator.hasNext()) {
            CreateRouteStopRequest element = iterator.next();
            Stop currentStop = new Stop();
            
            if (stationRepository.getByName(element.station()) == null) {
                throw new IllegalStateException("Wrong stop name, try with correct station name!");
            }

            currentStop.setArrivalTime(element.arrivalTime());
            currentStop.setDepartureTime(element.departureTime());
            currentStop.setCost(element.cost());
            currentStop.setStation(stationRepository.getByName(element.station()));
            currentStop.setMasterRoute(route);
            currentStop.setState(StopState.NOTPASSED);
            currentStop.setOrderInList(order++);
            stopRepository.save(currentStop);
        }

        repository.save(route);
        route.setDescription(request.description());
        route.setRouteState(RouteState.AVAILABLE);
        repository.save(route);
        return route;
    }

    public void addPiecesToRoute(Route route) {

        List<Stop> stops = stopRepository.findAllByMasterRoute(route);
        stops.sort(stopsComparatorByOrder);

        for (int i = 1; i < stops.size(); i++) {
            RoutePiece routePiece = new RoutePiece();
            routePiece.setMasterRoute(route);
            routePiece.setStartPoint(stops.get(i - 1));
            routePiece.setEndPoint(stops.get(i));
            routePieceRepository.save(routePiece);
        }
    }
}
