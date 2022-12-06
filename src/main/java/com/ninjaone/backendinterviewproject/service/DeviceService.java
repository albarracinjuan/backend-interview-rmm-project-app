package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.exceptions.BadRequestException;
import com.ninjaone.backendinterviewproject.exceptions.ExistingResourceException;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final String DEVICE_DOESNT_EXIST_MESSAGE = "Device doesn't exist";
    private static final String DEVICE_ALREADY_EXIST_MESSAGE = "Device already exists";
    private static final String DEVICE_TYPE_DOESNT_EXIST_MESSAGE = "Device type doesn't exist";
    private final CustomerService customerService;
    private final DeviceTypeService deviceTypeService;
    private final DeviceRepository deviceRepository;

    public DeviceService(CustomerService customerService, DeviceTypeService deviceTypeService, DeviceRepository deviceRepository) {
        this.customerService = customerService;
        this.deviceTypeService = deviceTypeService;
        this.deviceRepository = deviceRepository;
    }

    public void add(Long customerId, DeviceDTO deviceDTO) {
        Customer customer = customerService.retrieveExisting(customerId);
        DeviceType deviceType = validateDeviceType(deviceDTO.getDeviceTypeId());
        validateDuplicateDevice(deviceDTO);
        Device device = addDevice(deviceDTO, customer, deviceType);
        deviceDTO.setId(device.getId());
    }

    public List<DeviceDTO> retrieveAll(Long customerId) {
        Customer customer = customerService.retrieveExisting(customerId);
        List<Device> devices = retrieveAll(customer);

        return mapToDTO(devices);
    }

    public List<Device> retrieveAll(Customer customer) {

        return deviceRepository.findAllByCustomer(customer);
    }

    public void update(Long deviceId, DeviceDTO deviceDTO) {
        Device existingDevice = retrieveExisting(deviceId);
        validateDuplicateDevice(deviceDTO);
        existingDevice.setSystemName(deviceDTO.getSystemName());
        deviceRepository.save(existingDevice);
    }

    public void delete(Long deviceId) {
        try {
            deviceRepository.deleteById(deviceId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(DEVICE_DOESNT_EXIST_MESSAGE);
        }
    }

    public Device retrieveExisting(Long deviceId) {
        Optional<Device> existingDevice = deviceRepository.findById(deviceId);
        if (existingDevice.isEmpty()) {
            throw new ResourceNotFoundException(DEVICE_DOESNT_EXIST_MESSAGE);
        }

        return existingDevice.get();
    }

    public Device retrieveExisting(Customer customer, Long deviceId) {
        Optional<Device> existingDevice = deviceRepository.findDeviceByCustomerAndId(customer, deviceId);
        if (existingDevice.isEmpty()) {
            throw new ResourceNotFoundException(DEVICE_DOESNT_EXIST_MESSAGE);
        }

        return existingDevice.get();
    }

    private DeviceType validateDeviceType(Long deviceTypeId) {
        Optional<DeviceType> deviceType = deviceTypeService.retrieveExisting(deviceTypeId);
        if (deviceType.isEmpty()) {
            throw new BadRequestException(DEVICE_TYPE_DOESNT_EXIST_MESSAGE);
        }

        return deviceType.get();
    }

    private void validateDuplicateDevice(DeviceDTO deviceDTO) {
        if (deviceRepository.existsDeviceBySystemName(deviceDTO.getSystemName())) {
            throw new ExistingResourceException(DEVICE_ALREADY_EXIST_MESSAGE);
        }
    }

    private Device addDevice(DeviceDTO deviceDTO, Customer customer, DeviceType deviceType) {
        Device device = new Device(deviceDTO.getSystemName(), deviceType, customer);

        return deviceRepository.save(device);
    }

    private List<DeviceDTO> mapToDTO(List<Device> devices) {

        return devices.stream()
                .map(device -> new DeviceDTO(device.getId(), device.getSystemName(),
                        device.getDeviceType().getId(), device.getCustomer().getId()))
                .collect(Collectors.toList());
    }
}
