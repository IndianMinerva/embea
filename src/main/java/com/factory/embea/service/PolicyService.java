package com.factory.embea.service;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyRequest;

public interface PolicyService {
    Policy createPolicy(PolicyRequest policyRequest);

    Policy getPolicyDetails(PolicyDetailsRequest policyDetailsRequest);
}
