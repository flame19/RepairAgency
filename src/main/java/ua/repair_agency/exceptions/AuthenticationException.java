package ua.repair_agency.exceptions;

public class AuthenticationException extends RuntimeException{

    private Type type;

    public AuthenticationException(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        PASS,
        EMAIL
    }
}
