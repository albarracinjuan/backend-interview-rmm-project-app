package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.dto.CostReportDTO;
import com.ninjaone.backendinterviewproject.exceptions.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.Service;
import com.ninjaone.backendinterviewproject.model.ServicePerDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CostReportServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private CostReportService costReportService;

    @Test
    void whenGenerateReportAndCustomerDoesntExistThenExceptionShouldBeThrown() {
        given(customerService.retrieveExisting(any())).willThrow(new ResourceNotFoundException("Exception"));

        assertThrows(ResourceNotFoundException.class, () -> {
            costReportService.generateReport(1L);
        });
    }

    @Test
    void whenGenerateReportAndCustomerExistAndCustomerDoesntHaveDevicesThenCostReportShouldBeEmpty() {
        Customer existingCustomer = new Customer();
        given(customerService.retrieveExisting(any())).willReturn(existingCustomer);
        given(deviceService.retrieveAll(existingCustomer)).willReturn(Collections.emptyList());

        CostReportDTO costReportDTO = costReportService.generateReport(1L);

        assertEquals(0, costReportDTO.getTotalCost());
        assertTrue(costReportDTO.getCostPerServices().isEmpty());
    }

    @Test
    void whenGenerateReportAndCustomerExistAndCustomerHasDevicesThenCostReportShouldBeReturned() {
        Customer existingCustomer = new Customer();
        List<Device> existingDevices = prepareDevices(existingCustomer);
        given(customerService.retrieveExisting(any())).willReturn(existingCustomer);
        given(deviceService.retrieveAll(existingCustomer)).willReturn(existingDevices);

        CostReportDTO costReportDTO = costReportService.generateReport(1L);

        assertEquals(71, costReportDTO.getTotalCost());
        assertEquals(5, costReportDTO.getCostPerServices().size());
    }

    private List<Device> prepareDevices(Customer existingCustomer) {
        Device device1 = new Device("Windows 1", new DeviceType(), existingCustomer);
        Device device2 = new Device("Windows 2", new DeviceType(), existingCustomer);
        Device device3 = new Device("Mac 1", new DeviceType(), existingCustomer);
        Device device4 = new Device("Mac 2", new DeviceType(), existingCustomer);
        Device device5 = new Device("Mac 3", new DeviceType(), existingCustomer);

        Service windowsAntivirus = new Service("Windows Antivirus", Double.valueOf(5));
        Service macAntivirus = new Service("Mac Antivirus", Double.valueOf(7));
        Service backup = new Service("Backup", Double.valueOf(3));
        Service screenShare = new Service("Screen Share", Double.valueOf(1));

        device1.getServicesPerDevice()
                .addAll(prepareServicesPerDevice(device1, Arrays.asList(windowsAntivirus, backup, screenShare)));
        device2.getServicesPerDevice()
                .addAll(prepareServicesPerDevice(device2, Arrays.asList(windowsAntivirus, backup, screenShare)));
        device3.getServicesPerDevice()
                .addAll(prepareServicesPerDevice(device3, Arrays.asList(macAntivirus, backup, screenShare)));
        device4.getServicesPerDevice()
                .addAll(prepareServicesPerDevice(device4, Arrays.asList(macAntivirus, backup, screenShare)));
        device5.getServicesPerDevice()
                .addAll(prepareServicesPerDevice(device5, Arrays.asList(macAntivirus, backup, screenShare)));

        return Arrays.asList(device1, device2, device3, device4, device5);
    }

    private List<ServicePerDevice> prepareServicesPerDevice(Device device, List<Service> services) {

        return services.stream()
                .map(service -> new ServicePerDevice(service, device))
                .collect(Collectors.toList());
    }
}