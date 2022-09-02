package com.factory.embea.controller;

import com.factory.embea.model.request.InsuranceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class InsuranceController {

    @PostMapping
    public ResponseEntity<InsuranceRequest> createInsurance(@Valid @RequestBody InsuranceRequest insuranceRequest) {
        System.out.println(insuranceRequest.getStartDate());
        return ResponseEntity.ok(insuranceRequest);
    }
}
