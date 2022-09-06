package com.factory.embea.service.impl;

import com.factory.embea.entity.Policy;
import com.factory.embea.model.request.InsuredPerson;
import com.factory.embea.model.request.PolicyCreationRequest;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PolicyServiceImplSpec {
    @Autowired
    private PolicyServiceImpl policyServiceImpl;

    @MockBean
    private PolicyRepository policyRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    @DisplayName("Test - getEventsByVenue success")
    public void testGetEventsByVenue() {
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

}
