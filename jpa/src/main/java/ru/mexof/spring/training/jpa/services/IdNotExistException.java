package ru.mexof.spring.training.jpa.services;

public class IdNotExistException extends Exception {
    public IdNotExistException(Throwable cause) {
        super(cause);
    }
}
