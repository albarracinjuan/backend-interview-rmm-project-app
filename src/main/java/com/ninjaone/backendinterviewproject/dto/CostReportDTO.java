package com.ninjaone.backendinterviewproject.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CostReportDTO {

    @JsonProperty("total_cost")
    private final double totalCost;

    @JsonProperty("explanation")
    private final Map<String, Double> costPerServices;

    public CostReportDTO(double totalCost, Map<String, Double> costPerServices) {
        this.totalCost = totalCost;
        this.costPerServices = costPerServices;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Map<String, Double> getCostPerServices() {
        return costPerServices;
    }
}
