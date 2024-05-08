package com.technomad.diplomaupdated.controller.passenger;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.request.GetPlacesByStopNamesRequest;
import com.technomad.diplomaupdated.model.request.GetPlacesRequest;
import com.technomad.diplomaupdated.model.request.ReservePlacesByStopNamesRequest;
import com.technomad.diplomaupdated.model.request.ReservePlacesRequest;
import com.technomad.diplomaupdated.model.response.Place;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import com.technomad.diplomaupdated.repository.TicketRepository;
import com.technomad.diplomaupdated.service.BookService;
import com.technomad.diplomaupdated.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private RouteRepository routeRepository;
    private StopRepository stopRepository;
    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;

    @GetMapping(path = "places")
    public ResponseEntity<?> getPlaces(@RequestBody GetPlacesRequest request) {

        Route route = routeRepository.getReferenceById(request.route());
        Stop start = stopRepository.getReferenceById(request.start());
        Stop finish = stopRepository.getReferenceById(request.finish());
        Long startId = request.start();
        Long finishId = request.finish();

        return getResponseEntity(route, start, finish, startId, finishId);
    }

    @GetMapping(path = "places/by-name")
    public ResponseEntity<?> getPlacesByStopNames(@RequestBody GetPlacesByStopNamesRequest request) {

//        Route route = routeRepository.getReferenceById(request.route());
        Route route = routeRepository.findById(request.route()).orElseThrow(() -> new IllegalStateException("There is no Route with such id!"));
        Stop start = stopRepository.findStopByMasterRouteAndStation_Name(route, request.start()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station name in this Route! (Start Stop is missing)"));
        Stop finish = stopRepository.findStopByMasterRouteAndStation_Name(route, request.finish()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station namei in this Route! (Final Stop is missing)"));
        Long startId = start.getId();
        Long finishId = finish.getId();

        return getResponseEntity(route, start, finish, startId, finishId);
    }

    private ResponseEntity<?> getResponseEntity(Route route, Stop start, Stop finish, Long startId, Long finishId) {
        checkSelectedStopOrders(startId, finishId, route);

        List<Place> result = bookService.getReservedPlaces(route, start, finish);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PostMapping(path = "reserve")
    public ResponseEntity<?> reservePlaces(@AuthenticationPrincipal AppUser passenger, @RequestBody ReservePlacesRequest request) {

        Route route = routeRepository.findById(request.route()).orElseThrow(() -> new IllegalStateException("There is no Route with such id!"));
        Stop start = stopRepository.findById(request.start()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station name in this Route! (Start Stop is missing)"));
        Stop finish = stopRepository.findById(request.finish()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station name in this Route! (Final Stop is missing)"));
        Integer place = request.place();
        Long startId = start.getId();
        Long finishId = finish.getId();

        checkSelectedStopOrders(startId, finishId, route);

        Ticket ticket = new Ticket(passenger, route, start, finish, place);
        bookService.reserve(route, start, finish, place, ticket.getUuid());
        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketMapper.ticketToTicketDto(ticket));
    }

    @PostMapping(path = "reserve/by-name")
    public ResponseEntity<?> reservePlacesByStopNames(@AuthenticationPrincipal AppUser passenger, @RequestBody ReservePlacesByStopNamesRequest request) {

        Route route = routeRepository.findById(request.route()).orElseThrow(() -> new IllegalStateException("There is no Route with such id!"));
        Stop start = stopRepository.findStopByMasterRouteAndStation_Name(route, request.start()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station name in this Route! (Start Stop is missing)"));
        Stop finish = stopRepository.findStopByMasterRouteAndStation_Name(route, request.finish()).orElseThrow(() -> new IllegalStateException("There is no any Stop with such Station namei in this Route! (Final Stop is missing)"));
        Integer place = request.place();
        Long startId = start.getId();
        Long finishId = finish.getId();

        checkSelectedStopOrders(startId, finishId, route);

        Ticket ticket = new Ticket(passenger, route, start, finish, place);
        bookService.reserve(route, start, finish, place, ticket.getUuid());
        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketMapper.ticketToTicketDto(ticket));
    }

    public void checkSelectedStopOrders(Long startId, Long finishId, Route route) {
        if (startId >= finishId) {
            throw new IllegalStateException("Stop orders are not correct!");
        }

        if (!route.hasStopId(startId) || !route.hasStopId(finishId)) {
            throw new IllegalStateException("These stop(s) do(es)n't belong to this route");
        }
    }
}
