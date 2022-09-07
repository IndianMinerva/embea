package com.factory.embea.service.impl;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.InsuredPerson;
import com.factory.embea.model.request.InsuredPersonWithId;
import com.factory.embea.model.request.PolicyCreationRequest;
import com.factory.embea.model.request.PolicyModificationRequest;
import com.factory.embea.repository.PolicyRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PolicyServiceImplTest {
    @Autowired
    private PolicyServiceImpl policyServiceImpl;

    @MockBean
    private PolicyRepository policyRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    @DisplayName("Test - When a valid CreatePolicyRequest is Given, Policy should be created")
    public void giveValidPolicyCreationRequest_shouldCreatePolicy() {
        //given
        var policyCreationRequest = new PolicyCreationRequest(
                "01.01.3000",
                List.of(new InsuredPerson("AAA", "BBB", 5.0d),
                        new InsuredPerson("CCC", "DDD", 5.0d)
                ));
        var savedPolicy = mongoTemplate
                .save(policyServiceImpl.policyCreationRequestToPolicy(policyCreationRequest));
        Mockito.when(policyRepository
                        .findByPolicyIdAndStartDate(any(), any()))
                .thenReturn(Optional.ofNullable(mongoTemplate.findById(savedPolicy.getId(), Policy.class)));

        //when
        var mayBePolicy = policyServiceImpl.getPolicyDetails(savedPolicy.getPolicyId(), savedPolicy.getStartDate());

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(mayBePolicy.isPresent())
                    .as("Check if policy has been created")
                    .isEqualTo(true);
            softly.assertThat(mayBePolicy.get())
                    .as("Check if the policies are same")
                    .isEqualTo(savedPolicy);
        });
    }

    @Test
    @DisplayName("Test - Given new InsuredPersons, the new Insured Persons should be added to the Policy")
    public void givenNewInsuredPersons_theyShouldBeAddedToThePolicy() {
        //given
        var policyCreationRequest = new PolicyCreationRequest(
                "01.01.3000",
                List.of(new InsuredPerson("AAA", "BBB", 5.0d),
                        new InsuredPerson("CCC", "DDD", 5.0d)
                ));
        var savedPolicy = mongoTemplate
                .save(policyServiceImpl.policyCreationRequestToPolicy(policyCreationRequest));

        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest(
                savedPolicy.getPolicyId(),
                "01.01.4000",
                List.of(
                        new InsuredPersonWithId(null, "ASD", "FGH", 10.56d),
                        new InsuredPersonWithId(null, "UVW", "XYZ", 11.56d)
                )
        );

        Mockito.when(policyRepository
                        .findByPolicyId(any()))
                .thenReturn(Optional.ofNullable(mongoTemplate.findById(savedPolicy.getId(), Policy.class)));

        Mockito.when(policyRepository
                        .save(any()))
                .thenReturn(mongoTemplate.save(policyServiceImpl
                                .policyModificationRequestToPolicy(policyModificationRequest, savedPolicy)
                        )
                );
        //when
        var updatedPolicy = policyServiceImpl.updatePolicy(policyModificationRequest);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updatedPolicy.isPresent())
                    .as("Check if policy has been updated")
                    .isEqualTo(true);

            softly.assertThat(updatedPolicy.get().getPolicyId())
                    .as("Check if the policy ID did not change")
                    .isEqualTo(savedPolicy.getPolicyId());

            softly.assertThat(updatedPolicy.get()
                            .getInsuredPersons()
                            .stream()
                            .map(person -> person.getFirstName() + "|" + person.getLastName() + "|" + person.getPremium())
                            .collect(Collectors.toList()))
                    .as("Check if the first name and the last name matches")
                    .containsAll(policyModificationRequest
                            .getInsuredPersons()
                            .stream()
                            .map(person -> person.getFirstName() + "|" + person.getLastName() + "|" + person.getPremium())
                            .collect(Collectors.toList()));

            softly.assertThat(updatedPolicy.get().getEffectiveDate())
                    .as("Check if the Effective dates are same")
                    .isEqualTo(policyModificationRequest.getEffectiveDate()); //TODO: rename it to effective date

            softly.assertThat(updatedPolicy.get().getInsuredPersons().stream().filter(person -> person.getId() != null && person.getId() != 0)
                            .collect(Collectors.toSet()).size())
                    .as("Check if all the users have been assigned userIds and they are unique")
                    .isEqualTo(policyModificationRequest.getInsuredPersons().size());

            softly.assertThat(updatedPolicy.get().getTotalPremium())
                    .as("Check id the total premium is the sum of the individual premiums for Insured Persons")
                    .isEqualTo(policyModificationRequest.getInsuredPersons()
                            .stream()
                            .map(InsuredPersonWithId::getPremium)
                            .mapToDouble(Double::doubleValue)
                            .sum());
        });
    }

    @Test
    @DisplayName("Test - Given InsuredPersons, the existing Insured Persons should not be updated")
    public void givenExistingInsuredPersons_theyShouldNotBeUpdated() {
        //given
        var policyCreationRequest = new PolicyCreationRequest(
                "01.01.3000",
                List.of(new InsuredPerson("AAA", "BBB", 5.0d),
                        new InsuredPerson("CCC", "DDD", 5.0d)
                ));
        var createdPolicy = mongoTemplate
                .save(policyServiceImpl.policyCreationRequestToPolicy(policyCreationRequest));

        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest(
                createdPolicy.getPolicyId(),
                "01.01.4000",
                List.of(
                        new InsuredPersonWithId(1L, "ASD", "FGH", 10.56d),
                        new InsuredPersonWithId(null, "UVW", "XYZ", 11.56d)
                )
        );

        Mockito.when(policyRepository
                        .findByPolicyId(any()))
                .thenReturn(Optional.ofNullable(mongoTemplate.findById(createdPolicy.getId(), Policy.class)));

        Mockito.when(policyRepository
                        .save(any()))
                .thenReturn(mongoTemplate.save(policyServiceImpl
                                .policyModificationRequestToPolicy(policyModificationRequest, createdPolicy)
                        )
                );
        //when
        var updatedPolicy = policyServiceImpl.updatePolicy(policyModificationRequest);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updatedPolicy.isPresent())
                    .as("Check if policy has been updated")
                    .isEqualTo(true);

            softly.assertThat(updatedPolicy.get().getPolicyId())
                    .as("Check if the policy ID did not change")
                    .isEqualTo(createdPolicy.getPolicyId());

            softly.assertThat(updatedPolicy.get()
                            .getInsuredPersons()
                            .stream()
                            .map(person -> person.getFirstName() + "|" + person.getLastName() + "|" + person.getPremium())
                            .collect(Collectors.toList()))
                    .as("Check if the first name and the last name matches for the new InsuredPersons")
                    .contains(policyModificationRequest
                            .getInsuredPersons()
                            .stream()
                            .filter(person -> person.getId() != null && person.getId() != 0)
                            .map(person -> person.getFirstName() + "|" + person.getLastName() + "|" + person.getPremium())
                            .findFirst().get());

            softly.assertThat(updatedPolicy.get().getEffectiveDate())
                    .as("Check if the Effective dates are same")
                    .isEqualTo(policyModificationRequest.getEffectiveDate()); //TODO: rename it to effective date

            softly.assertThat(updatedPolicy.get().getInsuredPersons().stream().filter(person -> person.getId() != null && person.getId() != 0)
                            .collect(Collectors.toSet()).size())
                    .as("Check if all the users have been assigned userIds and they are unique")
                    .isEqualTo(policyModificationRequest.getInsuredPersons().size());
        });
    }

}
