package com.factory.embea.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PolicyCreationRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYYY")
    @Past
    private String startDate;
    private List<InsuredPerson> insuredPersons;
}
