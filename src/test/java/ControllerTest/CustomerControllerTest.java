package ControllerTest;

import com.rest.Entity.CustomerEntity;
import com.rest.contoller.CustomerController;
import com.rest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerService customerService;

    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(customer)).thenReturn(customer);
        ResponseEntity<?> responseEntity = customerController.createCustomer(customer);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(customer,responseEntity.getBody());
    }

    @Test
    void testNotCreateCustomer() {
        when(customerService.createCustomer(customer)).thenReturn(null);
        ResponseEntity<?> responseEntity = customerController.createCustomer(null);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
        assertEquals("Not able to create",responseEntity.getBody());
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        ResponseEntity<?> responseEntity = customerController.getCustomer(customer.getCustomerId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer,responseEntity.getBody());
    }

    @Test
    void testGetCustomerByNoId() {
        ResponseEntity<?> responseEntity = customerController.getCustomer(2L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<?> responseEntity = customerController.updateCustomer(1L,customer);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer,responseEntity.getBody());
    }

    @Test
    void testNoUpdateCustomer() {
        when(customerService.updateCustomer(customer)).thenReturn(null);
        ResponseEntity<?> responseEntity = customerController.updateCustomer(2L,customer);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
        assertEquals("Not able to Update",responseEntity.getBody());
    }

    @Test
    void testDeleteCustomer(){
        when(customerService.deleteCustomer(customer.getCustomerId())).thenReturn(true);
        ResponseEntity<?> responseEntity = customerController.deleteCustomer(customer.getCustomerId());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    @Test
    void testNoDeleteCustomer(){
        when(customerService.deleteCustomer(customer.getCustomerId())).thenReturn(false);
        ResponseEntity<?> responseEntity = customerController.deleteCustomer(2L);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());

    }
}
