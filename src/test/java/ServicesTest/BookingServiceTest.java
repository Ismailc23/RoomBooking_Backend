package ServicesTest;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import com.rest.Repository.RoomRepository;
import com.rest.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookingService.class)
@AutoConfigureMockMvc
public class BookingServiceTest {

    @Autowired
    BookingService bookingService;

    @MockBean
    BookingRepository bookingRepository;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    RoomRepository roomRepository;

    private BookingEntity booking;

    private CustomerEntity customer;

    @MockBean
    private RoomEntity room;

    private BookingPatchDTO bookingPatchDTO;

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
        roomRepository.save(room);

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

        bookingPatchDTO = new BookingPatchDTO();

    }

    @Test
    void testCreateBookingSuccess()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        BookingEntity booking1 = bookingService.createBookings(1L,100L,booking);
        assertEquals(booking,booking1);
    }

    @Test
    void testCreateBookingFailureNoCustomer()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        BookingEntity booking1 = bookingService.createBookings(1L,100L,booking);
        assertNull(booking1);
    }


    @Test
    void testCreateBookingFailureNoRoom()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(roomRepository.findById(100L)).thenReturn(Optional.empty());
        BookingEntity booking1 = bookingService.createBookings(1L,100L,booking);
        assertNull(booking1);
    }

    @Test
    void testCreateBookingFailureNotAvailable()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        room.setAvailable(false);
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        BookingEntity booking1 = bookingService.createBookings(1L,100L,booking);
        assertNull(booking1);
    }

    @Test
    void testGetBookingsByReferenceIdSuccess(){
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Optional<BookingEntity> getBooking = bookingService.getBookingsByReferenceId(1L);
        assertNotNull(getBooking);
    }

    @Test
    void testGetBookingsByReferenceIdFailure(){
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<BookingEntity> getBooking = bookingService.getBookingsByReferenceId(2L);
        assertEquals(Optional.empty(),getBooking);
    }

    @Test
    void testGetBookingsByCustomerIdAndRoomNumberSuccess()
    {
        when(bookingRepository.findByCustomer_CustomerIdAndRoom_RoomNumber(1L,100L)).thenReturn(Optional.of(booking));
        Optional<BookingEntity> getBooking = bookingService.getBookingsByCustomerIdAndRoomNumber(100L,1L);
        assertNotNull(getBooking);
    }

    @Test
    void testGetBookingsByCustomerIdAndRoomNumberFailure()
    {
        when(bookingRepository.findByCustomer_CustomerIdAndRoom_RoomNumber(1L,100L)).thenReturn(Optional.empty());
        Optional<BookingEntity> getBooking = bookingService.getBookingsByCustomerIdAndRoomNumber(100L,1L);
        assertEquals(Optional.empty(),getBooking);
    }

    @Test
    void testGetPatchBookingSucess()
    {
        bookingPatchDTO.setBookedDate(Date.from(LocalDate.of(2024, 6, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayEndDate(Date.from(LocalDate.of(2024, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(booking);
        BookingEntity patchBooking = bookingService.patchBooking(1L, bookingPatchDTO);
        assertNotNull(patchBooking);
    }

    @Test
    void testGetPatchBookingFailure()
    {
        when(bookingRepository.findById(2L)).thenReturn(Optional.empty());
        BookingEntity patchBooking = bookingService.patchBooking(1L,bookingPatchDTO);
        assertNull(patchBooking);
    }

    @Test
    void testGetPatchBookingWithoutBookedDate()
    {
        bookingPatchDTO.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayEndDate(Date.from(LocalDate.of(2024, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(booking);
        BookingEntity patchBooking = bookingService.patchBooking(1L, bookingPatchDTO);
        assertNotNull(patchBooking);
    }

    @Test
    void testGetPatchBookingWithoutStayEndDate()
    {
        bookingPatchDTO.setBookedDate(Date.from(LocalDate.of(2024, 6, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookingPatchDTO.setStayStartDate(Date.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(booking);
        BookingEntity patchBooking = bookingService.patchBooking(1L, bookingPatchDTO);
        assertNotNull(patchBooking);
    }
}
