package com.factory.embea.controller;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyRequest;
import com.factory.embea.service.PolicyService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PolicyController {

    @Autowired
    private final PolicyService policyService;

    @PostMapping
    @Timed(value = "policy.create", description = "Time taken to create a policy")
    public ResponseEntity<Policy> createPolicy(@RequestBody PolicyRequest policyRequest) {
        return ResponseEntity.ok(policyService.createPolicy(policyRequest)); //Always succeeds
    }

    @GetMapping
    public ResponseEntity<Policy> getPolicyDetails(@RequestBody PolicyDetailsRequest policyDetailsRequest) {
        System.out.println(policyDetailsRequest.getPolicyId() + "      " + policyDetailsRequest.getStartDate());
        return ResponseEntity.ok(policyService.getPolicyDetails(policyDetailsRequest));
    }
}
