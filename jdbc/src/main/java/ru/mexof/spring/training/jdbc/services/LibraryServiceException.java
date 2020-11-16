package ru.mexof.spring.training.jdbc.services;

public class LibraryServiceException extends Exception {
    public LibraryServiceException(String message) {
        super(message);
    }

    public LibraryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryServiceException(Throwable cause) {
        super(cause);
    }
}
