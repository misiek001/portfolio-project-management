package com.misiek.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessRelationManager extends Employee {

    @OneToMany(mappedBy = "businessRelationManager")
    private Set<Project> projects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Director director;

}
