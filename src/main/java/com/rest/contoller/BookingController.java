package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.ExceptionHandling.BookingExceptions.*;
import com.rest.ExceptionHandling.CustomerExceptions.CustomerNotFoundException;
import com.rest.ExceptionHandling.RoomExceptions.RoomNotFoundException;
import com.rest.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/api/customers/{id}/{roomNumber}")
    public ResponseEntity<?> createBooking(@PathVariable("id") Long customerId, @PathVariable("roomNumber") Long roomNumber,@RequestBody BookingEntity bookings) {
            BookingEntity createdBooking = bookingService.createBookings(customerId, roomNumber, bookings);
            log.debug("Booking is done successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping("/bookings/{referenceId}")
    public ResponseEntity<?> getBookings(@PathVariable long referenceId) {
            Optional<BookingEntity> bookings = bookingService.getBookingsByReferenceId(referenceId);
            log.debug("Fetching booking succesfful");
            return ResponseEntity.status(HttpStatus.OK).body(bookings.get());
    }

    @GetMapping("customers/{id}/{roomNumber}")
    public ResponseEntity<?> getBookingByCustomerIdAndRoomNumber(@PathVariable("id") Long customerId,@PathVariable("roomNumber") Long roomNumber) {
            Optional<BookingEntity> bookings = bookingService.getBookingsByCustomerIdAndRoomNumber(customerId, roomNumber);
            log.debug("Fetching booking succesful");
            return ResponseEntity.status(HttpStatus.OK).body(bookings.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchBooking(@PathVariable Long id, @RequestBody BookingPatchDTO bookingPatchDTO) {
        Optional<BookingEntity> patchBooking = Optional.ofNullable(bookingService.patchBooking(id, bookingPatchDTO));
        if(patchBooking.isPresent()) {
            return ResponseEntity.ok().body(patchBooking.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Not able to update the Booking");
        }
    }
}

