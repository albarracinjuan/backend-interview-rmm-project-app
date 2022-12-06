package com.ninjaone.backendinterviewproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICE")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private Double cost;

    public Service() {
    }

    public Service(String service, Double cost) {
        this.service = service;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public Double getCost() {
        return cost;
    }
}
