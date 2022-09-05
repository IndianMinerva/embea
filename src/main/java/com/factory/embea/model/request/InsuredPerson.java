package com.factory.embea.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredPerson {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private Double premium;
}
