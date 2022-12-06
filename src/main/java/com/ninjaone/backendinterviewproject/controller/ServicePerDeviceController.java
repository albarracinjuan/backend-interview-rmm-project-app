package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.dto.ServicePerDeviceDTO;
import com.ninjaone.backendinterviewproject.service.ServicePerDeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers/{customerId}/devices/{deviceId}/services")
public class ServicePerDeviceController {

    private final ServicePerDeviceService servicePerDeviceService;

    public ServicePerDeviceController(ServicePerDeviceService servicePerDeviceService) {
        this.servicePerDeviceService = servicePerDeviceService;
    }

    @PostMapping
    public ResponseEntity add(@PathVariable Long customerId, @PathVariable Long deviceId,
                              @RequestBody ServicePerDeviceDTO servicePerDeviceDTO) {
        servicePerDeviceService.add(customerId, deviceId, servicePerDeviceDTO);

        return new ResponseEntity(servicePerDeviceDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity delete(@PathVariable Long customerId, @PathVariable Long deviceId, @PathVariable Long serviceId) {
        servicePerDeviceService.delete(customerId, deviceId, serviceId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
