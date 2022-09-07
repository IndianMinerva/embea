package com.factory.embea.service;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.PolicyCreationRequest;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyModificationRequest;
import com.factory.embea.model.response.PolicyCreationResponse;
import com.factory.embea.model.response.PolicyModificationResponse;

import java.util.Optional;

public interface PolicyService {
    PolicyCreationResponse createPolicy(PolicyCreationRequest policyCreationRequest);

    Optional<Policy> getPolicyDetails(PolicyDetailsRequest policyDetailsRequest);

    Optional<Policy> getPolicyDetails(String policyId, String startDate);

    Optional<PolicyModificationResponse> updatePolicy(PolicyModificationRequest policyModificationRequest);
}
