package com.factory.embea.model.request;

import com.factory.embea.constraint.FutureDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PolicyCreationRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYYY")
    @FutureDate(message = "The startDate must be in the future")
    private String startDate;
    private List<InsuredPerson> insuredPersons;
}
