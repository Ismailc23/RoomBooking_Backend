package com.rest.ExceptionHandling.RoomExceptions;

public class RoomNumberAlreadyExistException extends RuntimeException {
    public RoomNumberAlreadyExistException(String message) {
        super(message);
    }
}
