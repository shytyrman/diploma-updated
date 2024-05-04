package com.technomad.diplomaupdated.service.ticket;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface TicketCheckService {

   public Boolean check(UUID ticketUuid, Long routeId);
}
