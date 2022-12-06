package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsDeviceBySystemName(String systemName);

    List<Device> findAllByCustomer(Customer customer);

    Optional<Device> findDeviceByCustomerAndId(Customer customer, Long id);
}
