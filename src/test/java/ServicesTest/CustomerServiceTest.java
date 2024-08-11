package ServicesTest;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.CustomerEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import com.rest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CustomerService.class)
@AutoConfigureMockMvc
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerService customerService;

    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        customerRepository.save(customer);
    }

    @Test
    void testNotUniqueUserNameCreateCustomer()
    {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setUserName("Hippo1");
        customer1.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        customer1.setCustomerId(2L);
        customer1.setFirstName("Mohammed ");
        customer1.setLastName("Ismail");
        customer1.setEmail("mohammedismailc76@gmai.com");
        customer1.setPassword("password");
        when(customerRepository.findByUserName(Mockito.any())).thenReturn(Optional.ofNullable(customer));
        CustomerEntity createdCustomer = customerService.createCustomer(customer1);
        assertNull(createdCustomer);
    }

    @Test
    void testIsCustomerAbove18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertTrue(result);
    }

    @Test
    void testIsCustomerUnder18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2024, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertFalse(result);
    }

    @Test
    void testCreateCustomerabove18(){
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        assertEquals(customer, createdCustomer);
    }

    @Test
    void testCreateCustomerbelow18(){
        customer.setDateOfBirth(Date.from(LocalDate.of(2010, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        assertNull(createdCustomer);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Optional<CustomerEntity> retrievedCustomer = customerService.getCustomerById(1L);
        assertTrue(retrievedCustomer.isPresent());
        assertEquals(customer, retrievedCustomer.get());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<CustomerEntity> retrievedCustomer = customerService.getCustomerById(2L);
        assertFalse(retrievedCustomer.isPresent());
    }

    @Test
    void testUpdateCustomer()
    {
        BookingEntity booking = new BookingEntity();
        booking.setReferenceId(1L);
        booking.setCustomerFirstName(customer.getFirstName());
        booking.setCustomerLastName(customer.getLastName());
        booking.setCustomer(customer);
        List<BookingEntity> bookings = new ArrayList<>();
        bookings.add(booking);

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setCustomerId(1L);
        updatedCustomer.setFirstName("Chenoth");
        updatedCustomer.setLastName("Ismail");
        updatedCustomer.setUserName("Hippo12");
        updatedCustomer.setEmail("mohammedismailc76@gmail.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByUserName(updatedCustomer.getUserName())).thenReturn(false);
        when(bookingRepository.findByCustomer_CustomerId(1L)).thenReturn(bookings);

        CustomerEntity updatedCustomer1 = customerService.updateCustomer(updatedCustomer);
        assertNotNull(updatedCustomer1);
    }

    @Test
    void testUpdateCustomerNotFound()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        CustomerEntity noUpdate = customerService.updateCustomer(customer);
        assertNull(noUpdate);
    }

    @Test
    void testUpdateCustomerUserNameALreadyExist()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setUserName("Hippo12");
        updatedCustomer.setCustomerId(1L);
        updatedCustomer.setFirstName("Chenoth");
        updatedCustomer.setLastName("Ismail");
        updatedCustomer.setUserName("Hippo12");
        updatedCustomer.setEmail("mohammedismailc76@gmail.com");
        when(customerRepository.existsByUserName(updatedCustomer.getUserName())).thenReturn(true);
        CustomerEntity noUpdates = customerService.updateCustomer(updatedCustomer);
        assertNull(noUpdates);
    }

    @Test
    void testUpdateCustomerNoBooking()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByUserName("Hippo12")).thenReturn(false);
        when(bookingRepository.findByCustomer_CustomerId(1L)).thenReturn(new ArrayList<>());
        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setUserName("Hippo12");
        updatedCustomer.setCustomerId(1L);
        updatedCustomer.setFirstName("Chenoth");
        updatedCustomer.setLastName("Ismail");
        updatedCustomer.setUserName("Hippo12");
        updatedCustomer.setEmail("mohammedismailc76@gmail.com");
        CustomerEntity noUpdates = customerService.updateCustomer(updatedCustomer);
        assertNotNull(noUpdates);
    }

    @Test
    void testdeleteCustomerSuccess()
    {
        when(customerRepository.existsById(1L)).thenReturn(true);
        boolean deleteData = customerService.deleteCustomer(1L);
        assertTrue(deleteData);
    }

    @Test
    void testNoDeleteCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(false);
        boolean deleteData = customerService.deleteCustomer(1L);
        assertFalse(deleteData);
    }
}
