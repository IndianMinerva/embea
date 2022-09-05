package com.factory.embea.service;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyCreationRequest;
import com.factory.embea.model.request.PolicyModificationRequest;

import java.util.Optional;

public interface PolicyService {
    Policy createPolicy(PolicyCreationRequest policyCreationRequest);

    Optional<Policy> getPolicyDetails(PolicyDetailsRequest policyDetailsRequest);

    Optional<Policy> getPolicyDetails(String policyId, String startDate);

    Optional<Policy> updatePolicy(PolicyModificationRequest policyModificationRequest);
}
