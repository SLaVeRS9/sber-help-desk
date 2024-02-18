package ru.sberbank.edu.ticketservice.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TicketStatus {
    @JsonProperty("new")
    NEW("new"),

    @JsonProperty("assigned")
    ASSIGNED("assigned"),

    @JsonProperty("inProgress")
    IN_PROGRESS("inProgress"),

    @JsonProperty("closed")
    CLOSED("closed"),

    @JsonProperty("archived")
    ARCHIVED("archived");

    TicketStatus(String s) {
    }
}
