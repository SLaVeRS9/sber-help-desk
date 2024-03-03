package ru.sberbank.edu.common.error.exception;

public class EditTicketException extends RuntimeException {
    public EditTicketException() {

    }
    public EditTicketException(String message) {
        super(message);
    }
    public EditTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
