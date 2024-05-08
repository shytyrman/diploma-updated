package com.technomad.diplomaupdated.service.mapper;

import com.technomad.diplomaupdated.model.Ticket;
import com.technomad.diplomaupdated.model.response.TicketDto;
import com.technomad.diplomaupdated.model.response.TicketInfos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketMapper {

    public TicketInfos ticketToTicketInfos(Ticket ticket) {

        return new TicketInfos(ticket.getFromStop().getStation().getName(), ticket.getToStop().getStation().getName(), ticket.getPlace(), ticket.getTicketState());
    }

    public TicketDto ticketToTicketDto(Ticket ticket) {

//        TicketDto result =
//
//        TicketDto.builder()
//                .ticketOwner(ticket.getTicketOwner())
//                .place(ticket.getPlace())
//                .uuid(ticket.getUuid())
//                .ticketState(ticket.getTicketState())
//                .forRoute(ticket.getForRoute())
//                .fromStop(ticket.getFromStop())
//                .toStop(ticket.getToStop()).build();

        TicketDto result = new TicketDto(
                ticket.getId(),
                ticket.getTicketOwner(),
                ticket.getPlace(),
                ticket.getUuid(),
                ticket.getTicketState(),
                ticket.getForRoute(),
                ticket.getFromStop(),
                ticket.getToStop()
        );

        return result;
    }

    public List<TicketDto> ticketListToTicketDtoList(List<Ticket> tickets) {

        List<TicketDto> ticketDtos = new ArrayList<>();

        for (Ticket ticket : tickets
             ) {
            ticketDtos.add(ticketToTicketDto(ticket));
        }

        return ticketDtos;
    }
}
