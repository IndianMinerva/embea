package com.factory.embea.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuredPerson {
    private String firstName;
    private String lastName;
    private Float premium;
}
