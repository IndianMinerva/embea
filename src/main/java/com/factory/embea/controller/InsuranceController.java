package com.factory.embea.controller;

import com.factory.embea.model.request.PolicyRequest;
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
    //@Timed(value = "policy.create", description = "Time taken to create a policy")
    public ResponseEntity<PolicyRequest> createPolicy(@Valid @RequestBody PolicyRequest policyRequest) {
        System.out.println(policyRequest.getStartDate());
        return ResponseEntity.ok(policyRequest);
    }
}
