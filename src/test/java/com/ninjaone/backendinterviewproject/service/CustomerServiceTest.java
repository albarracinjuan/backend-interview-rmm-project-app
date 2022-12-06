package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void whenRetrieveExistingAndDoesntExistThenExceptionShouldBeThrown() {
        given(customerRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
           customerService.retrieveExisting(1L);
        });
    }

    @Test
    void whenRetrieveExistingAndExistsThenCustomerShouldBeReturned() {
        given(customerRepository.findById(any())).willReturn(Optional.of(new Customer()));

        Customer customer = customerService.retrieveExisting(1L);

        assertNotNull(customer);
    }

}