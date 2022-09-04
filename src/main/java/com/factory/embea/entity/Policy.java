package com.factory.embea.entity;

import com.factory.embea.model.request.InsuredPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
    @Getter
    private String id;

    @Getter
    private String startDate;

    @Getter
    private List<InsuredPerson> insuredPersons;
}
