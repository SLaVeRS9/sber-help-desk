package ru.sberbank.edu.common.error;

public class ActionNotAllowException extends RuntimeException {
    public ActionNotAllowException() {

    }
    public ActionNotAllowException(String message) {
        super(message);
    }
    public ActionNotAllowException(String message, Throwable cause) {
        super(message, cause);
    }
}
