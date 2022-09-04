package com.factory.embea.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PolicyRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.YYYY")
    @Past
    private Date startDate;
    private List<InsuredPerson> insuredPersons;
}
