package com.rest.ExceptionHandling.BookingExceptions;

public class OverlappingDatesException extends RuntimeException {
    public OverlappingDatesException(String message) {
        super(message);
    }
}
