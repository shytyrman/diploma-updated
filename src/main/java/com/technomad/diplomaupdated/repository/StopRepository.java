package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.state.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.StopState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

//    public List<Stop> findAllByDepartureTime_Date(LocalDate departureTime_date);
    List<Stop> findAllByStationName(String name);
    List<Stop> findAllByState(StopState state);
    List<Stop> findAllByMasterRoute(Route route);
    List<Stop> findAllByStationNameAndState(String name, StopState state);
    List<Stop> findAllByStationNameAndStateAndMasterRouteRouteState(String stopName, StopState stopState, RouteState routeState);
    List<Stop> findAllByStationNameAndStateAndMasterRouteRouteStateAndDepartureTime(String stopName, StopState state, RouteState routeState, LocalDateTime localDateTime);
    Boolean existsStopsByMasterRoute(Route route);
    Optional<Stop> findStopByMasterRouteAndStation_Name(Route route, String stationName);
}
