package com.technomad.diplomaupdated.service.mapper;

import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.response.TicketInfos;
import org.springframework.stereotype.Service;

@Service
public class TicketMapper {

    public TicketInfos ticketToTicketInfos(Ticket ticket) {

        return new TicketInfos(ticket.getFromStop().getStation().getName(), ticket.getToStop().getStation().getName(), ticket.getPlace());
    }
}
