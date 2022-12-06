package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    public Optional<DeviceType> retrieveExisting(Long deviceTypeId) {

        return deviceTypeRepository.findById(deviceTypeId);
    }
}
