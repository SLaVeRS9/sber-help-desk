package ru.sberbank.edu.ticketservice.ticket.enums;

import lombok.Getter;

@Getter
public enum TicketFormat {

    SHORT ("short"),
    LONG("long");

    private final String name;
    TicketFormat(String name) {
        this.name = name;
    }
}
