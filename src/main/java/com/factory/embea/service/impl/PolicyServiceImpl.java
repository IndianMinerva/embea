package com.factory.embea.service.impl;

import com.factory.embea.repository.PolicyRepository;
import com.factory.embea.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
}
