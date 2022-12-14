package com.factory.embea.controller;

import com.factory.embea.model.request.PolicyCreationRequest;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyModificationRequest;
import com.factory.embea.model.response.PolicyCreationResponse;
import com.factory.embea.model.response.PolicyDetailsResponse;
import com.factory.embea.model.response.PolicyModificationResponse;
import com.factory.embea.service.PolicyService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PolicyController {

    @Autowired
    private final PolicyService policyService;

    @PostMapping
    @Timed(value = "policy.create", description = "Time taken to create a policy")
    public ResponseEntity<PolicyCreationResponse> createPolicy(@Valid @RequestBody PolicyCreationRequest policyCreationRequest) {
        return ResponseEntity.ok(policyService.createPolicy(policyCreationRequest)); //Always succeeds
    }

    @GetMapping
    public ResponseEntity<Optional<PolicyDetailsResponse>> getPolicyDetails(@RequestBody PolicyDetailsRequest policyDetailsRequest) {
        return policyService.getPolicyDetails(policyDetailsRequest)
                .map(policy -> ResponseEntity.ok(policyService.getPolicyDetails(policyDetailsRequest)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping //Ideally @PatchMapping should be used
    public ResponseEntity<PolicyModificationResponse> updatePolicy(@Valid @RequestBody PolicyModificationRequest policyModificationRequest) {
        return policyService.updatePolicy(policyModificationRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
}
