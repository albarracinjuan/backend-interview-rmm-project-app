package com.ninjaone.backendinterviewproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE_SERVICE")
public class ServicePerDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public ServicePerDevice() {
    }

    public ServicePerDevice(Service service, Device device) {
        this.service = service;
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public Device getDevice() {
        return device;
    }
}
