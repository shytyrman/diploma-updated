package com.technomad.diplomaupdated.service.ticket;

import com.technomad.diplomaupdated.exception.IllegalRequestException;
import com.technomad.diplomaupdated.repository.TicketCodeHolderRepository;
import com.technomad.diplomaupdated.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
@Qualifier("Minimal")
public class MinimalTicketCheckService implements TicketCheckService{

    private final TicketRepository ticketRepository;
    private final TicketCodeHolderRepository ticketCodeHolderRepository;
    @Override
    public Boolean check(UUID uuid, Long routeId) {

//        ticketRepository.findTicketByUuidAndForRoute_Id(uuid, routeId).orElseThrow(() -> new IllegalStateException("Ticket is missing!"));
//        ticketCodeHolderRepository.findTicketCodeHolderByUuidAndMasterRoutePiece_MasterRoute_Id(uuid, routeId).orElseThrow(() -> new IllegalStateException("Ticket code is missing!"));
        if (!ticketCodeHolderRepository.existsTicketCodeHolderByUuidAndMasterRoutePiece_MasterRoute_Id(uuid, routeId)) {
            throw new IllegalRequestException("Ticket is missing in route!");
        }
        return true;
    }
}
