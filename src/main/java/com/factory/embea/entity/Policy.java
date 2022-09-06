package com.factory.embea.entity;

import com.factory.embea.model.request.InsuredPersonWithId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
@NoArgsConstructor
@Getter
public class Policy {
    @Id
    @JsonIgnore
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof Policy otherPolicy) {
            return Objects.equals(otherPolicy.getId(), this.getId())
                    && Objects.equals(otherPolicy.getPolicyId(), this.getPolicyId())
                    && Objects.equals(otherPolicy.getStartDate(), this.getStartDate())
                    && Objects.equals(otherPolicy.getInsuredPersons(), this.getInsuredPersons())
                    && Objects.equals(otherPolicy.getTotalPremium(), this.getTotalPremium());
        }
        return false;
    }
}
