package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
