package com.technomad.diplomaupdated.service.ticket;

import java.util.UUID;

public interface TicketCheckService {

    Boolean check(UUID ticketUuid, Long routeId);
}
