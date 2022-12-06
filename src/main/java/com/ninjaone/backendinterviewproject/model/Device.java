package com.ninjaone.backendinterviewproject.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEVICE")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "system_name", nullable = false)
    private String systemName;

    @ManyToOne
    @JoinColumn(name = "device_type_id", nullable = false)
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<ServicePerDevice> servicesPerDevice;

    public Device() {
    }

    public Device(String systemName, DeviceType deviceType, Customer customer) {
        this.systemName = systemName;
        this.deviceType = deviceType;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<ServicePerDevice> getServicesPerDevice() {
        if (servicesPerDevice == null) {
            servicesPerDevice = new ArrayList<>();
        }

        return servicesPerDevice;
    }
}
