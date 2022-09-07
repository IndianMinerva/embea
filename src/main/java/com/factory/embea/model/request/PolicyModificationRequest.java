package com.factory.embea.model.request;

import com.factory.embea.constraint.FutureDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PolicyModificationRequest {
    private String policyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYYY")
    @FutureDate(message = "The effectiveDate must be in the future")
    private String effectiveDate;

    private List<InsuredPersonWithId> insuredPersons;
}
