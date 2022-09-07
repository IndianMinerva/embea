package com.factory.embea.model.response;

import com.factory.embea.model.request.InsuredPerson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PolicyCreationResponse {
    private String startDate;

    private List<InsuredPerson> insuredPersons;
}
