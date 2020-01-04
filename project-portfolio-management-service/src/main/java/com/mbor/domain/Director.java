package com.mbor.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Director extends Employee {

    @OneToMany(mappedBy = "director")
    @Fetch(value = FetchMode.JOIN)
    private Set<Supervisor> employees = new HashSet<>();

    @OneToMany(mappedBy = "director")
    @Fetch(value = FetchMode.JOIN)
    private Set<BusinessRelationManager> businessRelationManagers = new HashSet<>();

}
