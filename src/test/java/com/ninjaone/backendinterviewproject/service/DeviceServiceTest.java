package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.exceptions.BadRequestException;
import com.ninjaone.backendinterviewproject.exceptions.ExistingResourceException;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private DeviceTypeService deviceTypeService;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void whenAddAndCustomerDoesntExistThenExceptionShouldBeThrown() {
        DeviceDTO deviceDTO = new DeviceDTO();
        given(customerService.retrieveExisting(any())).willThrow(new ResourceNotFoundException("Exception"));

        assertThrows(ResourceNotFoundException.class, () -> {
           deviceService.add(1L, deviceDTO);
        });
    }

    @Test
    void whenAddAndCustomerExistAndDeviceTypeIsNotValidThenExceptionShouldBeThrown() {
        DeviceDTO deviceDTO = new DeviceDTO();
        given(customerService.retrieveExisting(any())).willReturn(new Customer());
        given(deviceTypeService.retrieveExisting(any())).willReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> {
            deviceService.add(1L, deviceDTO);
        });
    }

    @Test
    void whenAddAndCustomerExistAndDeviceTypeIsValidAndDeviceIsDuplicatedThenExceptionShouldBeThrown() {
        DeviceDTO deviceDTO = new DeviceDTO(null, "Name", 1L, 1L);
        given(customerService.retrieveExisting(any())).willReturn(new Customer());
        given(deviceTypeService.retrieveExisting(any())).willReturn(Optional.of(new DeviceType()));
        given(deviceRepository.existsDeviceBySystemName(any())).willReturn(Boolean.TRUE);

        assertThrows(ExistingResourceException.class, () -> {
            deviceService.add(1L, deviceDTO);
        });
    }

    @Test
    void whenAddAndCustomerExistAndDeviceTypeIsValidAndDeviceIsNotDuplicatedThenExceptionShouldBeThrown() {
        DeviceDTO deviceDTO = new DeviceDTO(null, "Name", 1L, 1L);
        given(customerService.retrieveExisting(any())).willReturn(new Customer());
        given(deviceTypeService.retrieveExisting(any())).willReturn(Optional.of(new DeviceType()));
        given(deviceRepository.existsDeviceBySystemName(any())).willReturn(Boolean.FALSE);
        given(deviceRepository.save(any())).willReturn(new Device());

        deviceService.add(1L, deviceDTO);

        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void whenRetrieveAllAndCustomerDoesntExistThenExceptionShouldBeThrown() {
        given(customerService.retrieveExisting(any())).willThrow(new ResourceNotFoundException("Exception"));

        assertThrows(ResourceNotFoundException.class, () -> {
           deviceService.retrieveAll(1L);
        });
    }

    @Test
    void whenRetrieveAllAndCustomerExistAndCustomerDoesntHaveDevicesThenEmptyListShouldBeReturned() {
        Customer customer = new Customer();
        given(customerService.retrieveExisting(any())).willReturn(customer);
        given(deviceRepository.findAllByCustomer(customer)).willReturn(Collections.emptyList());

        List<DeviceDTO> devicesDTOS = deviceService.retrieveAll(1L);

        assertTrue(CollectionUtils.isEmpty(devicesDTOS));
    }

    @Test
    void whenRetrieveAllAndCustomerExistAndCustomerHasDevicesThenDevicesListShouldBeReturned() {
        Customer customer = new Customer();
        Device device1 = new Device("Device 1", new DeviceType(), customer);
        Device device2 = new Device("Device 2", new DeviceType(), customer);
        given(customerService.retrieveExisting(any())).willReturn(customer);
        given(deviceRepository.findAllByCustomer(customer)).willReturn(Arrays.asList(device1, device2));

        List<DeviceDTO> devicesDTOS = deviceService.retrieveAll(1L);

        assertTrue(devicesDTOS.size() == 2);
    }

    @Test
    void whenUpdateAndDeviceDoesntExistThenExceptionShouldBeThrown() {
        given(deviceRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            deviceService.update(1L, new DeviceDTO());
        });
    }

    @Test
    void whenUpdateAndDeviceExistAndUpdatedDeviceIsDuplicatedThenExceptionShouldBeThrown() {
        DeviceDTO deviceDTO = new DeviceDTO(null, "New Name", 1L, 1L);
        Device device = new Device("Device 1", new DeviceType(), new Customer());
        given(deviceRepository.findById(any())).willReturn(Optional.of(device));
        given(deviceRepository.existsDeviceBySystemName(deviceDTO.getSystemName())).willReturn(Boolean.TRUE);

        assertThrows(ExistingResourceException.class, () -> {
            deviceService.update(1L, deviceDTO);
        });
    }

    @Test
    void whenUpdateAndDeviceExistAndUpdatedDeviceIsNotDuplicatedThenDeviceShouldBeUpdated() {
        DeviceDTO deviceDTO = new DeviceDTO(null, "New Name", 1L, 1L);
        Device existingDevice = new Device("Device 1", new DeviceType(), new Customer());
        given(deviceRepository.findById(any())).willReturn(Optional.of(existingDevice));
        given(deviceRepository.existsDeviceBySystemName(deviceDTO.getSystemName())).willReturn(Boolean.FALSE);

        deviceService.update(1L, deviceDTO);

        verify(deviceRepository, times(1)).save(existingDevice);
        assertEquals(deviceDTO.getSystemName(), existingDevice.getSystemName());
    }
}