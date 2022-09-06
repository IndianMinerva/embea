package com.factory.embea.entity;

import com.factory.embea.model.request.InsuredPersonWithId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@Getter
public class Policy {
    @Id
    private ObjectId id;
    private String policyId;
    private String startDate;
    private List<InsuredPersonWithId> insuredPersons;
    private Double totalPremium;

    public Policy(String policyId, String startDate, List<InsuredPersonWithId> insuredPersons, Double totalPremium) {
        this.policyId = policyId;
        this.startDate = startDate;
        this.insuredPersons = insuredPersons;
        this.totalPremium = totalPremium;
    }

    public Policy(ObjectId id, String policyId, String startDate, List<InsuredPersonWithId> insuredPersons, Double totalPremium) {
        this.id = id;
        this.policyId = policyId;
        this.startDate = startDate;
        this.insuredPersons = insuredPersons;
        this.totalPremium = totalPremium;
    }
}
