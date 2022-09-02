package com.factory.embea.entity;

import com.factory.embea.model.request.InsuredPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Policy {
    @Id
    private String id;

    private String startDate;

    private List<InsuredPerson> insuredPersons;
}
