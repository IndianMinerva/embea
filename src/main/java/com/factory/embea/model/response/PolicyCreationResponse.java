package com.factory.embea.model.response;

import com.factory.embea.model.request.InsuredPersonWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PolicyCreationResponse {
    private String policyId;

    private String startDate;

    private List<InsuredPersonWithId> insuredPersons;

    private Double premium;
}
