package ru.sberbank.edu.common.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
@RequiredArgsConstructor
public class RestErrorHandler {
    private final ServiceError serviceError;
    @ExceptionHandler(value = { UserNotFoundException.class, TicketNotFoundException.class })
    public ResponseEntity<ServiceError> handleNotFoundError(RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        serviceError.setStatus(HttpStatus.NOT_FOUND.value());
        serviceError.setTimeStamp(new Date().getTime());
        serviceError.setDetails(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(serviceError);
    }

    @ExceptionHandler(ActionNotAllowException.class)
    public ResponseEntity<ServiceError> handleActionNotAllowError(ActionNotAllowException ex) {
        log.error(ex.getMessage(), ex);

        serviceError.setStatus(HttpStatus.FORBIDDEN.value());
        serviceError.setTimeStamp(new Date().getTime());
        serviceError.setDetails(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(serviceError);
    }
}
