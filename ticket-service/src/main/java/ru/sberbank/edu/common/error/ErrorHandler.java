package ru.sberbank.edu.common.error;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<ServiceError> handleError(UserNotFoundException ex) {
        ServiceError error = new ServiceError();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(new Date().getTime());
        error.setDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(error);
    }
}
