package com.factory.embea.service.impl;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.InsuredPerson;
import com.factory.embea.model.request.PolicyDetailsRequest;
import com.factory.embea.model.request.PolicyRequest;
import com.factory.embea.repository.PolicyRepository;
import com.factory.embea.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepository policyRepository;

    @Override
    public Policy createPolicy(PolicyRequest policyRequest) {
        String policyId = new ObjectId().toString();
        String startDate = policyRequest.getStartDate();
        List<InsuredPerson> insuredPersons = assignIdsToInsuredPersons(policyRequest.getInsuredPersons());
        Double totalPremium = insuredPersons.stream().map(InsuredPerson::getPremium).mapToDouble(Double::doubleValue).sum();
        return policyRepository.save(new Policy(policyId, startDate, insuredPersons, totalPremium));
    }

    @Override
    public Policy getPolicyDetails(PolicyDetailsRequest policyDetailsRequest) {
        return policyRepository.findByPolicyIdAndStartDate(policyDetailsRequest.getPolicyId(), policyDetailsRequest.getStartDate());
    }

    private List<InsuredPerson> assignIdsToInsuredPersons(List<InsuredPerson> insuredPersons) {
        return IntStream.range(0, insuredPersons.size())
                .mapToObj(userId -> {
                    var insuredPerson = insuredPersons.get(userId);
                    return new InsuredPerson(userId + 1, insuredPerson.getFirstName(), insuredPerson.getLastName(), insuredPerson.getPremium());
                })
                .collect(Collectors.toList());
    }
}
