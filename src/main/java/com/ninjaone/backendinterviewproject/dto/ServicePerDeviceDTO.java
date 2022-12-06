package com.ninjaone.backendinterviewproject.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicePerDeviceDTO {

    private Long id;

    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("deviceId")
    private Long deviceId;

    public ServicePerDeviceDTO() {
    }

    public ServicePerDeviceDTO(Long id, Long serviceId, Long deviceId) {
        this.id = id;
        this.serviceId = serviceId;
        this.deviceId = deviceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }
}
