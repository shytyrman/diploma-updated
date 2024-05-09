package com.technomad.diplomaupdated.controller.passenger;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.response.TicketDto;
import com.technomad.diplomaupdated.repository.TicketRepository;
import com.technomad.diplomaupdated.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    @GetMapping(path = "tickets")
    public ResponseEntity<?> getMyTickets(@AuthenticationPrincipal AppUser appUser) {

        List<Ticket> tickets = ticketRepository.findAllByTicketOwner(appUser);
        List<TicketDto> result = ticketMapper.ticketListToTicketDtoList(tickets);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "ticket")
    public ResponseEntity<?> getTicketById(@AuthenticationPrincipal AppUser appUser, @RequestParam Long ticketId) {

        Ticket ticket = ticketRepository.findTicketByTicketOwnerAndId(appUser, ticketId).orElseThrow(() -> new IllegalStateException("Current user doen't have such ticket with this id!"));
        TicketDto result = ticketMapper.ticketToTicketDto(ticket);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "ticket/uuid")
    public ResponseEntity<?> getTicketUuid(@AuthenticationPrincipal AppUser appUser, @RequestParam Long ticketId) {

        List<Ticket> userTickets = ticketRepository.findAllByTicketOwner(appUser);
        String result = "";


        for (Ticket ticket : userTickets
             ) {
            if (ticket.getId().equals(ticketId)) {
                result = ticket.getUuid().toString();
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This ticket does not exist or doesn't belong to this user");
    }
}
