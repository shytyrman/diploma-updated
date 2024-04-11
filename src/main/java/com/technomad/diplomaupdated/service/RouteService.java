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

    public Route addRoute(CreateRouteRequest request, AppUser appUser) {

        ArrayList<CreateRouteStopRequest> stopRequests = request.getStops();
        Iterator<CreateRouteStopRequest> iterator = stopRequests.iterator();
        Route route = new Route();
        route.setDriver(appUser);
        List<Stop> stops = route.getRouteStations();
        int order = 0;
//
        repository.save(route);
        while (iterator.hasNext()) {
            CreateRouteStopRequest element = iterator.next();
            Stop currentStop = new Stop();
            
            if (stationRepository.getByName(element.getStation()) == null) {
                throw new IllegalStateException("Wrong stop name, try with correct station name!");
            }

            currentStop.setArrivalTime(element.getArrivalTime());
            currentStop.setDepartureTime(element.getDepartureTime());
            currentStop.setCost(element.getCost());
            currentStop.setStation(stationRepository.getByName(element.getStation()));
            currentStop.setMasterRoute(route);
            currentStop.setState(StopState.NOTPASSED);
            currentStop.setOrderInList(order++);
            stopRepository.save(currentStop);
        }

        repository.save(route);

//        Iterator<Stop> iteratorStop = route.getRouteStations().iterator();
//        Stop previous;
//        iteratorStop.

//        stops.sort(stopsComparatorByOrder);
//
//        for (int i = 1; i < 5; i++) {
//            RoutePiece routePiece = new RoutePiece();
//            routePiece.setMasterRoute(route);
////            routePiece.setStartPoint(route.getRouteStations().get(i));
////            routePiece.setEndPoint(route.getRouteStations().get(i + 1));
//            routePiece.setStartPoint(stops.get(i));
//            routePieceRepository.save(routePiece);
//        }
//
        route.setDescription(request.getDescription());
        route.setRouteState(RouteState.AVAILABLE);
        repository.save(route);
        return route;
    }

//    public ArrayList<Route> getRoutes(AppUser appUser) {
//        ArrayList<Route> result = repository.findRoutesByDriver(appUser.getUsername().toString()).get();
//        return result;
//    }

    public Route addPiecesToRoute(Route route) {

        List<Stop> stops = stopRepository.findAllByMasterRoute(route);
        stops.sort(stopsComparatorByOrder);

        for (int i = 1; i < stops.size(); i++) {
            RoutePiece routePiece = new RoutePiece();
            routePiece.setMasterRoute(route);
//            routePiece.setStartPoint(route.getRouteStations().get(i));
//            routePiece.setEndPoint(route.getRouteStations().get(i + 1));
            routePiece.setStartPoint(stops.get(i - 1));
            routePiece.setEndPoint(stops.get(i));
            routePieceRepository.save(routePiece);
        }

        return route;
    }
}
