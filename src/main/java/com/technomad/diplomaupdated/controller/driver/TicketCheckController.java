package com.technomad.diplomaupdated.controller.driver;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.response.TicketInfos;
import com.technomad.diplomaupdated.model.state.TicketState;
import com.technomad.diplomaupdated.repository.TicketCodeHolderRepository;
import com.technomad.diplomaupdated.repository.TicketRepository;
import com.technomad.diplomaupdated.service.mapper.TicketMapper;
import com.technomad.diplomaupdated.service.ticket.TicketCheckService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class TicketCheckController {

    private final TicketCodeHolderRepository ticketCodeHolderRepository;
    private final TicketRepository ticketRepository;
    private final TicketCheckService ticketCheckService;
    private final TicketMapper ticketMapper;

    @GetMapping(path = "ticket/check")
    public ResponseEntity<?> isTicketValid(@AuthenticationPrincipal AppUser appUser, @RequestParam String ticketUuid, @RequestParam Long routeId) {

        UUID uuid = UUID.fromString(ticketUuid);
//        try {
            if (!ticketCheckService.check(uuid, routeId)) {
                throw new IllegalStateException("Something is not compatible with uuid and routeId");
            }
                Ticket passengerTicket = ticketRepository.findTicketByUuidAndForRoute_Id(uuid, routeId).orElseThrow(() -> new IllegalStateException("Ticket is missing"));
                TicketInfos result = ticketMapper.ticketToTicketInfos(passengerTicket);
                passengerTicket.setTicketState(TicketState.CHECKED);
                ticketRepository.save(passengerTicket);
                return ResponseEntity.status(HttpStatus.OK).body(result);

//            }
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body("The ticket is not valid!");
    }
    }
