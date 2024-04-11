package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.request.GetPlacesRequest;
import com.technomad.diplomaupdated.model.request.ReservePlacesRequest;
import com.technomad.diplomaupdated.model.response.Place;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import com.technomad.diplomaupdated.repository.TicketRepository;
import com.technomad.diplomaupdated.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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

    @GetMapping(path = "places")
    public ResponseEntity<?> getPlaces(@RequestBody GetPlacesRequest request) {

        Route route = routeRepository.getReferenceById(request.getRoute());
        Stop start = stopRepository.getReferenceById(request.getStart());
        Stop finish = stopRepository.getReferenceById(request.getFinish());
        Long startId = request.getStart();
        Long finishId = request.getFinish();

        if (!route.hasStopId(startId) || !route.hasStopId(finishId)) {
            throw new IllegalStateException("These stop(s) do(es)n't belong to this route");
        }

        List<Place> result = bookService.getReservedPlaces(route, start, finish);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PostMapping(path = "reserve")
    public ResponseEntity<?> reservePlaces(@AuthenticationPrincipal AppUser passenger, @RequestBody ReservePlacesRequest request) {

        Route route = routeRepository.findById(request.getRoute()).get();
        Stop start = stopRepository.findById(request.getStart()).get();
        Stop finish = stopRepository.findById(request.getFinish()).get();
        Integer place = request.getPlace();

        Ticket ticket = new Ticket(passenger, route, start, finish, place);
        ticketRepository.save(ticket);

        bookService.reserve(route, start, finish, place, ticket.getUuid());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Places are reserved!");
    }
}
