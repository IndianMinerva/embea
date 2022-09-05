package com.factory.embea.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PolicyModificationRequest {
    private String policyId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYYY")
    private String startDate;
    private List<InsuredPersonWithId> insuredPersons;
}
