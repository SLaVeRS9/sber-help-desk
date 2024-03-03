package ru.sberbank.edu.common.error.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {

    }
    public TicketNotFoundException(String message) {
        super(message);
    }
    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
