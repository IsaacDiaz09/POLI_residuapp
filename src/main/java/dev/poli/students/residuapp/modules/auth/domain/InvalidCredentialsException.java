package dev.poli.students.residuapp.modules.auth.domain;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
