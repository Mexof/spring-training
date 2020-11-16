package ru.mexof.spring.training.jdbc.services;

public class IdNotExistException extends Exception {
    public IdNotExistException(Throwable cause) {
        super(cause);
    }
}
