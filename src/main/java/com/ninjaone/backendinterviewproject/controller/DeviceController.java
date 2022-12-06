package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers/{customerId}/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity add(@PathVariable Long customerId, @RequestBody DeviceDTO deviceDTO) {
        deviceService.add(customerId, deviceDTO);

        return new ResponseEntity(deviceDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(@PathVariable Long customerId) {

        return new ResponseEntity(deviceService.retrieveAll(customerId), HttpStatus.OK);
    }

    @PatchMapping("/{deviceId}")
    public ResponseEntity update(@PathVariable Long deviceId, @RequestBody DeviceDTO deviceDTO) {
        deviceService.update(deviceId, deviceDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity delete(@PathVariable Long deviceId) {
        deviceService.delete(deviceId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
