package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServicePerDevice;
import com.ninjaone.backendinterviewproject.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicePerDeviceRepository extends JpaRepository<ServicePerDevice, Long> {

    boolean existsServicePerDeviceByDeviceAndService(Device device, Service service);

    Optional<ServicePerDevice> findServicePerDeviceByDeviceAndId(Device device, Long id);
}
