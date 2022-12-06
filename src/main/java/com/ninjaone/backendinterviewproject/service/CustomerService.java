package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer retrieveExisting(Long customerId) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);
        if (existingCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer doesn't exist");
        }

        return existingCustomer.get();
    }
}
