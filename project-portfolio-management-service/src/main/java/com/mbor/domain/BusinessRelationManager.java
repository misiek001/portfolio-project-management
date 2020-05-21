package com.mbor.domain;

import com.mbor.exception.BusinessUnitAlreadyAssignedException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessRelationManager extends Employee {

    @OneToMany(mappedBy = "businessRelationManager")
    @Fetch(value = FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "director_id")
    @Fetch(value = FetchMode.JOIN)
    private Director director;

    @OneToMany(mappedBy = "businessRelationManager")
    @Fetch(value = FetchMode.JOIN)
    private Set<BusinessUnit> assignedBusinessUnits = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
        director.getBusinessRelationManagers().add(this);
    }

    public Set<BusinessUnit> getAssignedBusinessUnits() {
        return assignedBusinessUnits;
    }

    public void addAssignedBusinessUnit(BusinessUnit businessUnit){
        if(assignedBusinessUnits.contains(businessUnit)){
            throw new BusinessUnitAlreadyAssignedException();
        }
        this.assignedBusinessUnits.add(businessUnit);
        businessUnit.setBusinessRelationManager(this);

    }
}
