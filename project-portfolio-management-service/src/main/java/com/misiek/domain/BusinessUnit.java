package com.misiek.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Business Unit")
@Table(name = "business_unit")
@Getter
@Setter
@NoArgsConstructor
public class BusinessUnit {

    @Id
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "businessUnits")
    private Set<Project> projects = new HashSet<>();
}
