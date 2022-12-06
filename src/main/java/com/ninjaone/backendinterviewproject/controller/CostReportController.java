package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.dto.CostReportDTO;
import com.ninjaone.backendinterviewproject.service.CostReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customers/{customerId}/cost-report")
public class CostReportController {

    private final CostReportService costReportService;

    public CostReportController(CostReportService costReportService) {
        this.costReportService = costReportService;
    }

    @GetMapping
    public ResponseEntity generateReport(@PathVariable Long customerId) {

        return new ResponseEntity(costReportService.generateReport(customerId), HttpStatus.OK);
    }
}
