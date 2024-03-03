package ru.sberbank.edu.common.error.handler;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.edu.common.error.ServiceError;
import ru.sberbank.edu.common.error.exception.ActionNotAllowException;
import ru.sberbank.edu.common.error.exception.TicketNotFoundException;
import ru.sberbank.edu.common.error.exception.UserNotFoundException;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
@RequiredArgsConstructor
public class UIErrorHandler {
    private final ServiceError serviceError;

    @ExceptionHandler(value = { UserNotFoundException.class, TicketNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundError(RuntimeException ex, Model model) {
        log.error(ex.getMessage(), ex);

        serviceError.setStatus(HttpStatus.NOT_FOUND.value());
        serviceError.setTimeStamp(new Date().getTime());
        serviceError.setDetails(ex.getMessage());
        model.addAttribute("serviceError", serviceError);

        return "error-page";
    }

    @ExceptionHandler(ActionNotAllowException.class)
    public String handleActionNotAllowError(ActionNotAllowException ex, Model model) {
        log.error(ex.getMessage(), ex);

        serviceError.setStatus(HttpStatus.FORBIDDEN.value());
        serviceError.setTimeStamp(new Date().getTime());
        serviceError.setDetails(ex.getMessage());

        model.addAttribute("serviceError", serviceError);

        return "error-page";
    }
}
