package com.mbor.domain;

import com.mbor.exception.ProjectAlreadyAssignedAsPrimaryException;
import com.mbor.exception.ProjectAlreadyAssignedAsSecondaryException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class BusinessUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NaturalId
    private String name;

    @OneToMany(mappedBy = "businessUnit")
    @Fetch(value = FetchMode.JOIN)
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private BusinessRelationManager businessRelationManager;

    @OneToMany(mappedBy = "primaryBusinessUnit")
    private Set<Project> primaryProjects = new HashSet<>();

    @ManyToMany(mappedBy = "secondaryBusinessUnits")
    @Fetch(value = FetchMode.JOIN)
    private Set<Project> secondaryProjects = new HashSet<>();

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Project> getPrimaryProjects() {
        return primaryProjects;
    }

    public void addPrimaryProject(Project project){
        if(getSecondaryProjects().contains(project)){
            throw new ProjectAlreadyAssignedAsSecondaryException();
        }
        primaryProjects.add(project);
    }

    public Set<Project> getSecondaryProjects() {
        return secondaryProjects;
    }

    public void addSecondaryProject(Project project){
        if(getPrimaryProjects().contains(project)){
            throw new ProjectAlreadyAssignedAsPrimaryException();
        }
        secondaryProjects.add(project);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessRelationManager getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManager businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessUnit that = (BusinessUnit) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
