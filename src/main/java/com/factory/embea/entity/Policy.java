package com.factory.embea.entity;

import com.factory.embea.model.request.InsuredPerson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Policy {
    private String policyId;
    private String startDate;
    private List<InsuredPerson> insuredPersons;
    private Double totalPremium;
}
