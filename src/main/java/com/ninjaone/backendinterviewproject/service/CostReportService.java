package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.dto.CostReportDTO;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServicePerDevice;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostReportService {

    //TODO move these to database
    private static final Double COST_PER_DEVICE = Double.valueOf(4);
    private static final String DEVICE_SERVICE_NAME = "Device";
    private static final String TOTAL = "total";
    private final CustomerService customerService;
    private final DeviceService deviceService;

    public CostReportService(CustomerService customerService, DeviceService deviceService) {
        this.customerService = customerService;
        this.deviceService = deviceService;
    }

    public CostReportDTO generateReport(Long customerId) {
        Customer customer = customerService.retrieveExisting(customerId);
        List<Device> devices = deviceService.retrieveAll(customer);

        return calculateCost(devices);
    }

    private CostReportDTO calculateCost(List<Device> devices) {
        Map<String, Double> costPerServices = new HashMap<>();
        Double totalCost = Double.valueOf(0);
        if (!CollectionUtils.isEmpty(devices)) {
            addDevicesCost(devices, costPerServices);
            devices.forEach(device -> calculateCost(device, costPerServices));
            totalCost = costPerServices.get(TOTAL);
            costPerServices.remove(TOTAL);
        }

        return new CostReportDTO(totalCost, costPerServices);
    }

    private void addDevicesCost(List<Device> devices, Map<String, Double> costPerServices) {
        Double devicesCost = devices.size() * COST_PER_DEVICE;
        costPerServices.put(DEVICE_SERVICE_NAME, devicesCost);
        costPerServices.put(TOTAL, devicesCost);
    }

    private void calculateCost(Device device, Map<String, Double> costPerServices) {
        device.getServicesPerDevice().forEach(servicePerDevice -> calculateCost(servicePerDevice, costPerServices));
    }

    private void calculateCost(ServicePerDevice servicePerDevice, Map<String, Double> costPerServices) {
        String serviceName = servicePerDevice.getService().getService();
        Double servicePerDeviceCost = costPerServices.containsKey(serviceName) ?
                costPerServices.get(serviceName) + servicePerDevice.getService().getCost() : servicePerDevice.getService().getCost();
        costPerServices.put(serviceName, servicePerDeviceCost);
        costPerServices.put(TOTAL, costPerServices.get(TOTAL) + servicePerDevice.getService().getCost());
    }
}
