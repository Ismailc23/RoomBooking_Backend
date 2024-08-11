package com.rest.contoller;

import com.rest.ExceptionHandling.CustomerExceptions.CustomerNotFoundException;
import com.rest.ExceptionHandling.CustomerExceptions.InvalidAgeException;
import com.rest.ExceptionHandling.UserException.UserNameAlreadyExistException;
import com.rest.services.CustomerService;
import com.rest.Entity.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/request/api/customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerEntity customer) {
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        log.debug("Customer is created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/request/api/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
            Optional<CustomerEntity> customer = customerService.getCustomerById(id);
            log.debug("Customer with ID : {} is present", id);
            return ResponseEntity.ok().body(customer);
        }

    @PutMapping("/request/api/customer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer) {
        customer.setCustomerId(id);
        CustomerEntity updatedCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
        }


    @DeleteMapping("/request/api/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }
}