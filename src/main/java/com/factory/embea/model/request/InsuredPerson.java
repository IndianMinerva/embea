package com.factory.embea.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredPerson {
    private String firstName;
    private String lastName;
    private Double premium;
}
