package com.factory.embea.service.impl;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.*;
import com.factory.embea.model.response.PolicyCreationResponse;
import com.factory.embea.model.response.PolicyDetailsResponse;
import com.factory.embea.model.response.PolicyModificationResponse;
import com.factory.embea.repository.PolicyRepository;
import com.factory.embea.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private final PolicyRepository policyRepository;

    @Override
    public PolicyCreationResponse createPolicy(PolicyCreationRequest policyCreationRequest) {
        return toPolicyCreationResponse(policyRepository.save(policyCreationRequestToPolicy(policyCreationRequest)));
    }

    private PolicyCreationResponse toPolicyCreationResponse(Policy policy) {
        return new PolicyCreationResponse(
                policy.getPolicyId(),
                policy.getStartDate(),
                policy.getInsuredPersons(),
                policy.getTotalPremium());
    }

    @Override
    public Optional<PolicyDetailsResponse> getPolicyDetails(PolicyDetailsRequest policyDetailsRequest) {
        return policyRepository.findByPolicyIdAndStartDate(
                policyDetailsRequest.getPolicyId(),
                Optional.ofNullable(policyDetailsRequest.getRequestDate()).orElseGet(() -> DATE_FORMAT.format(new Date())
        )).map(this::toPolicyDetailsResponse);
    }

    private PolicyDetailsResponse toPolicyDetailsResponse(Policy policy) {
        return new PolicyDetailsResponse(
                policy.getPolicyId(),
                policy.getStartDate(),
                policy.getInsuredPersons(),
                policy.getTotalPremium()
        );
    }

    @Override
    public Optional<Policy> getPolicyDetails(String policyId, String startDate) {
        return policyRepository.findByPolicyIdAndStartDate(policyId, startDate);
    }

    @Override
    public Optional<PolicyModificationResponse> updatePolicy(PolicyModificationRequest policyModificationRequest) {
        Optional<Policy> maybePolicy = policyRepository.findByPolicyId(policyModificationRequest.getPolicyId());

        return maybePolicy.map(policy -> {
            Policy updatedPolicy = policyModificationRequestToPolicy(policyModificationRequest, policy);
            return Optional.of(toPolicyModificationResponse(policyRepository.save(updatedPolicy)));
        }).orElse(Optional.empty());
    }

    private PolicyModificationResponse toPolicyModificationResponse(Policy policy) {
        return new PolicyModificationResponse(
                policy.getPolicyId(),
                policy.getStartDate(),
                policy.getInsuredPersons(),
                policy.getTotalPremium()
        );
    }

    Policy policyCreationRequestToPolicy(PolicyCreationRequest policyCreationRequest) {
        String policyId = new ObjectId().toString();
        String startDate = policyCreationRequest.getStartDate();
        List<InsuredPersonWithId> insuredPersons = assignIdsToInsuredPersons(policyCreationRequest.getInsuredPersons());
        Double totalPremium = insuredPersons.stream()
                .map(InsuredPersonWithId::getPremium)
                .mapToDouble(Double::doubleValue)
                .sum();
        return new Policy(policyId, startDate, insuredPersons, totalPremium);
    }

    Policy policyModificationRequestToPolicy(PolicyModificationRequest policyModificationRequest, Policy savedPolicy) {
        List<InsuredPersonWithId> persons = assignIdsToModifiedUsers(policyModificationRequest, savedPolicy);
        return new Policy(
                savedPolicy.getId(),
                policyModificationRequest.getPolicyId(),
                policyModificationRequest.getEffectiveDate(),
                persons,
                persons.stream().map(InsuredPersonWithId::getPremium).mapToDouble(Double::doubleValue).sum()
        );
    }

    private List<InsuredPersonWithId> assignIdsToModifiedUsers(
            PolicyModificationRequest policyModificationRequest,
            Policy policy) {

        List<InsuredPersonWithId> matchingPersons = policyModificationRequest
                .getInsuredPersons()
                .stream().filter(person -> policy.getInsuredPersons().contains(person)).collect(Collectors.toCollection(ArrayList::new));

        List<InsuredPersonWithId> nonMatchingPersons = policyModificationRequest
                .getInsuredPersons()
                .stream().filter(person -> !policy.getInsuredPersons().contains(person)).toList();

        List<InsuredPersonWithId> modifiedInsuredPersons = nonMatchingPersons.stream()
                .map(person -> new InsuredPersonWithId(System.nanoTime(), person.getFirstName(), person.getLastName(), person.getPremium()))
                .collect(Collectors.toCollection(ArrayList::new));

        List<InsuredPersonWithId> persons = new ArrayList<>();
        persons.addAll(matchingPersons);
        persons.addAll(modifiedInsuredPersons);

        return persons;
    }

    private List<InsuredPersonWithId> assignIdsToInsuredPersons(List<InsuredPerson> insuredPersons) {
        return IntStream.range(0, insuredPersons.size())
                .mapToObj(userId -> {
                    var insuredPerson = insuredPersons.get(userId);
                    return new InsuredPersonWithId(
                            (long) userId + 1,
                            insuredPerson.getFirstName(),
                            insuredPerson.getLastName(),
                            insuredPerson.getPremium());
                })
                .collect(Collectors.toList());
    }
}
