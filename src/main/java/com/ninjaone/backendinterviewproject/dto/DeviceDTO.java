package com.ninjaone.backendinterviewproject.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO {

    private Long id;

    @JsonProperty("system_name")
    private String systemName;

    @JsonProperty("device_type_id")
    private Long deviceTypeId;

    @JsonProperty("customer_id")
    private Long customerId;

    public DeviceDTO() {
    }

    public DeviceDTO(Long id, String systemName, Long deviceTypeId, Long customerId) {
        this.id = id;
        this.systemName = systemName;
        this.deviceTypeId = deviceTypeId;
        this.customerId = customerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
