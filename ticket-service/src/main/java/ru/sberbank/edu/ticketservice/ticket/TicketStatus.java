package ru.sberbank.edu.ticketservice.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TicketStatus {
    @JsonProperty("new")
    NEW(1),

    @JsonProperty("assigned")
    ASSIGNED(2),

    @JsonProperty("inProgress")
    IN_PROGRESS(3),

    @JsonProperty("closed")
    CLOSED(4),

    @JsonProperty("archived")
    ARCHIVED(5);

    TicketStatus(Integer statusNumber) {
    }
}
