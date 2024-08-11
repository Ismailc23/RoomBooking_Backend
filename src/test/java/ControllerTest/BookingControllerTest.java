package ControllerTest;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.contoller.BookingController;
import com.rest.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    BookingController bookingController;

    @MockBean
    BookingService bookingService;

    private BookingEntity booking;

    private CustomerEntity customer;

    private RoomEntity room;

    @BeforeEach
    void setUp()
    {
        customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");

        room = new RoomEntity();
        room.setRoomNumber(100L);
        room.setType("3BHK");
        room.setAvailable(true);
        room.setPrice(1000);

        booking = new BookingEntity();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCustomerFirstName(customer.getFirstName());
        booking.setCustomerFirstName(customer.getLastName());
        booking.setRoomType(room.getType());
        booking.setRoomPrice(room.getPrice());
        booking.setReferenceId(1L);
        booking.setBookedDate(Date.from(LocalDate.of(2024, 6, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setStayEndDate(Date.from(LocalDate.of(2024, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    void testCreateBooking()
    {
        when(bookingService.createBookings(1L,100L,booking)).thenReturn(booking);
        ResponseEntity<?> createdBooking = bookingController.createBooking(1L,100L,booking);
        assertEquals(HttpStatus.CREATED,createdBooking.getStatusCode());
        assertEquals(booking,createdBooking.getBody());
    }

    @Test
    void testNoCreateBooking()
    {
        when(bookingService.createBookings(1L,100L,booking)).thenReturn(null);
        ResponseEntity<?> createdBooking = bookingController.createBooking(1L,100L,booking);
        assertEquals(HttpStatus.NOT_FOUND,createdBooking.getStatusCode());
        assertEquals("Not able to create the booking",createdBooking.getBody());
    }

    @Test
    void testGetBookingByRefIdSuccess()
    {
        when(bookingService.getBookingsByReferenceId(1L)).thenReturn(Optional.of(booking));
        ResponseEntity<?> getCustomer = bookingController.getBookings(1L);
        assertEquals(HttpStatus.OK,getCustomer.getStatusCode());
        assertEquals(booking,getCustomer.getBody());
    }

    @Test
    void testNoGetBookingRefIdFailure()
    {
        when(bookingService.getBookingsByReferenceId(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> getCustomer = bookingController.getBookings(1L);
        assertEquals(HttpStatus.NOT_FOUND,getCustomer.getStatusCode());
    }

    @Test
    void testGetBookingByCustomerIdAndRoomNumberSuccess()
    {
        when(bookingService.getBookingsByCustomerIdAndRoomNumber(1L,100L)).thenReturn(Optional.of(booking));
        ResponseEntity<?> getCustomer = bookingController.getBookingByCustomerIdAndRoomNumber(1L,100L);
        assertEquals(HttpStatus.OK,getCustomer.getStatusCode());
        assertEquals(booking,getCustomer.getBody());
    }

    @Test
    void testGetBookingByCustomerIdAndRoomNumberFailure()
    {
        when(bookingService.getBookingsByCustomerIdAndRoomNumber(1L,100L)).thenReturn(Optional.empty());
        ResponseEntity<?> getCustomer = bookingController.getBookingByCustomerIdAndRoomNumber(1L,100L);
        assertEquals(HttpStatus.NOT_FOUND,getCustomer.getStatusCode());
    }

    @Test
    void testPatchBookingSucsess()
    {
        BookingPatchDTO bookingPatchDTO = new BookingPatchDTO();
        bookingPatchDTO.setBookedDate(Date.from(LocalDate.of(2024, 6, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayEndDate(Date.from(LocalDate.of(2024, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(bookingService.patchBooking(1L,bookingPatchDTO)).thenReturn(booking);
        ResponseEntity<?> patchBooking = bookingController.patchBooking(1L,bookingPatchDTO);
        assertEquals(HttpStatus.OK,patchBooking.getStatusCode());
        assertEquals(booking,patchBooking.getBody());
    }

    @Test
    void testPatchBookingFailure()
    {
        BookingPatchDTO bookingPatchDTO = new BookingPatchDTO();
        bookingPatchDTO.setBookedDate(Date.from(LocalDate.of(2024, 6, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayEndDate(Date.from(LocalDate.of(2024, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(bookingService.patchBooking(1L,bookingPatchDTO)).thenReturn(null);
        ResponseEntity<?> patchBooking = bookingController.patchBooking(1L,bookingPatchDTO);
        assertEquals(HttpStatus.NOT_FOUND,patchBooking.getStatusCode());
        assertEquals("Not able to update the Booking",patchBooking.getBody());
    }
}