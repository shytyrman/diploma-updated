package com.technomad.diplomaupdated.model.response;

import com.technomad.diplomaupdated.model.state.TicketState;

public record TicketInfos(
        String fromStop,
        String toStop,
        Integer place,

        TicketState ticketState
) {
}
