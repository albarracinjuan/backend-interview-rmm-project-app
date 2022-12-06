package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.ServicePerDeviceRepository;
import com.ninjaone.backendinterviewproject.dto.ServicePerDeviceDTO;
import com.ninjaone.backendinterviewproject.exceptions.BadRequestException;
import com.ninjaone.backendinterviewproject.exceptions.ExistingResourceException;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServicePerDevice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicePerDeviceService {

    private static final String SERVICE_DOESNT_EXIST_MESSAGE = "Service doesn't exist";
    private static final String SERVICE_PER_DEVICE_DOESNT_EXIST_MESSAGE = "Service per device doesn't exist";
    private static final String SERVICE_PER_DEVICE_ALREADY_EXIST_MESSAGE = "service per device already exists";
    private final ServicePerDeviceRepository servicePerDeviceRepository;
    private final CustomerService customerService;
    private final DeviceService deviceService;
    private final ServiceService serviceService;

    public ServicePerDeviceService(ServicePerDeviceRepository servicePerDeviceRepository, CustomerService customerService,
                                   DeviceService deviceService, ServiceService serviceService) {
        this.servicePerDeviceRepository = servicePerDeviceRepository;
        this.customerService = customerService;
        this.deviceService = deviceService;
        this.serviceService = serviceService;
    }

    public void add(Long customerId, Long deviceId, ServicePerDeviceDTO servicePerDeviceDTO) {
        Customer customer = customerService.retrieveExisting(customerId);
        Device device = deviceService.retrieveExisting(customer, deviceId);
        com.ninjaone.backendinterviewproject.model.Service service = validateService(servicePerDeviceDTO.getServiceId());
        validateDuplicateServicePerDevice(device, service);
        ServicePerDevice servicePerDevice = addServicePerDevice(device, service);
        servicePerDeviceDTO.setId(servicePerDevice.getId());
    }

    public void delete(Long customerId, Long deviceId, Long servicePerDeviceId) {
        Customer customer = customerService.retrieveExisting(customerId);
        Device device = deviceService.retrieveExisting(customer, deviceId);
        ServicePerDevice servicePerDevice = retrieveExisting(device, servicePerDeviceId);
        servicePerDeviceRepository.delete(servicePerDevice);
    }

    private com.ninjaone.backendinterviewproject.model.Service validateService(Long serviceId) {
        Optional<com.ninjaone.backendinterviewproject.model.Service> service = serviceService.retrieveExisting(serviceId);
        if (service.isEmpty()) {
            throw new BadRequestException(SERVICE_DOESNT_EXIST_MESSAGE);
        }

        return service.get();
    }

    private void validateDuplicateServicePerDevice(Device device, com.ninjaone.backendinterviewproject.model.Service service) {
        if (servicePerDeviceRepository.existsServicePerDeviceByDeviceAndService(device, service)) {
            throw new ExistingResourceException(SERVICE_PER_DEVICE_ALREADY_EXIST_MESSAGE);
        }
    }

    private ServicePerDevice addServicePerDevice(Device device, com.ninjaone.backendinterviewproject.model.Service service) {
        ServicePerDevice servicePerDevice = new ServicePerDevice(service, device);

        return servicePerDeviceRepository.save(servicePerDevice);
    }

    private ServicePerDevice retrieveExisting(Device device, Long servicePerDeviceId) {
        Optional<ServicePerDevice> existingServicePerDevice =
                servicePerDeviceRepository.findServicePerDeviceByDeviceAndId(device, servicePerDeviceId);
        if (existingServicePerDevice.isEmpty()) {
            throw new ResourceNotFoundException(SERVICE_PER_DEVICE_DOESNT_EXIST_MESSAGE);
        }

        return existingServicePerDevice.get();
    }
}
