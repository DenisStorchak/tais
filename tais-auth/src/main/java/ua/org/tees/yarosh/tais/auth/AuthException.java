package ua.org.tees.yarosh.tais.auth;

public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
