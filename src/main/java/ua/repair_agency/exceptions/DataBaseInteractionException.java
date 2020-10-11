package ua.repair_agency.exceptions;

public class DataBaseInteractionException extends RuntimeException {

    public DataBaseInteractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseInteractionException(String message) { super(message); }
}
