package com.factory.embea.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredPersonWithId {
    @Setter
    private Long id;
    private String firstName;
    private String lastName;
    private Double premium;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InsuredPersonWithId otherPerson) {
            return Objects.equals(id, otherPerson.getId());
        } else {
            return false;
        }
    }
}
