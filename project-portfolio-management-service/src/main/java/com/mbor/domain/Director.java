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

    public Set<Supervisor> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Supervisor> employees) {
        this.employees = employees;
    }

    public Set<BusinessRelationManager> getBusinessRelationManagers() {
        return businessRelationManagers;
    }

    public void setBusinessRelationManagers(Set<BusinessRelationManager> businessRelationManagers) {
        this.businessRelationManagers = businessRelationManagers;
    }
}
