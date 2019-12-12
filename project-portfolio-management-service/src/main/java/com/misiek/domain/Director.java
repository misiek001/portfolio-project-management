package com.misiek.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Director extends Employee {

    @OneToMany(mappedBy = "director")
    private Set<Supervisor> employees = new HashSet<>();

    @OneToMany(mappedBy = "director")
    private Set<BusinessRelationManager> businessRelationManagers = new HashSet<>();

}
