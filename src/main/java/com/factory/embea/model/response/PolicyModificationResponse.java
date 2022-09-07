package com.factory.embea.model.response;

import com.factory.embea.model.request.InsuredPersonWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PolicyModificationResponse {
    private String policyId;

    private String effectiveDate;

    private List<InsuredPersonWithId> insuredPersons;

    private Double totalPremium;
}
