package ru.sberbank.edu.ticketservice.error;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException() {
        super();
    }
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
