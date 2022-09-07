package com.factory.embea.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PolicyDetailsRequest {
    private String policyId;
    private String requestDate;
}
