package com.factory.embea.model.response;

import com.factory.embea.model.request.InsuredPersonWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PolicyDetailsResponse {
    private String policyId;

    private String requestDate;

    private List<InsuredPersonWithId> insuredPersons;

    private Double totalPremium;
}
