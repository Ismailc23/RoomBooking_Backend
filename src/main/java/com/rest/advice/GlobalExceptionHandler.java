package com.rest.advice;

import com.rest.ExceptionHandling.BookingExceptions.BookingNotFoundException;
import com.rest.ExceptionHandling.BookingExceptions.InvalidDateException;
import com.rest.ExceptionHandling.BookingExceptions.OverlappingDatesException;
import com.rest.ExceptionHandling.CustomerExceptions.CustomerNotFoundException;
import com.rest.ExceptionHandling.CustomerExceptions.InvalidAgeException;
import com.rest.ExceptionHandling.RoomExceptions.RoomNotFoundException;
import com.rest.ExceptionHandling.RoomExceptions.RoomNumberAlreadyExistException;
import com.rest.ExceptionHandling.UserException.InvalidCredentialsException;
import com.rest.ExceptionHandling.UserException.UserNameAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<?> handleInvalidAgeException(InvalidAgeException ex) {
        log.info("Reached InvalidAgeException");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistException(UserNameAlreadyExistException ex) {
        log.info("Reached UserNameAlreadyExistException");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.info("Reached InvalidCredentialException");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.info("Reached CustomerNotFoundException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNumberAlreadyExistException.class)
    public ResponseEntity<?> handleRoomNumberAlreadyExistException(RoomNumberAlreadyExistException ex) {
        log.info("Reached RoomNumberAlreadyExistException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<?> handleRoomNotFoundException(RoomNotFoundException ex) {
        log.info("Reached RoomNotFoundException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OverlappingDatesException.class)
    public ResponseEntity<?> handleOverlappingDatesException(OverlappingDatesException ex) {
        log.info("Reached OverlappingDatesException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<?> handleInvalidDateException(InvalidDateException ex) {
        log.info("Reached InvalidDateException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> hanldeBookingNotFoundException(BookingNotFoundException ex) {
        log.info("Reached BookingNotFoundException");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
}
