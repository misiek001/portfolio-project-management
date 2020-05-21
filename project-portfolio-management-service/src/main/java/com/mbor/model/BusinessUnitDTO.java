package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mbor.model.views.Views;

import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class BusinessUnitDTO extends IdDTO {

    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.BusinessUnitInternal.class)
    private Set<EmployeeDTO> employees = new HashSet<>();

    @JsonView(Views.BusinessUnitInternal.class)
    private BusinessRelationManagerDTO businessRelationManager;

    @JsonView(Views.BusinessUnitInternal.class)
    private Set<ProjectDTO> primaryProjects = new HashSet<>();

    @JsonView(Views.BusinessUnitInternal.class)
    private Set<ProjectDTO> secondaryProjects = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public BusinessRelationManagerDTO getBusinessRelationManager() {
        return businessRelationManager;
    }

    public void setBusinessRelationManager(BusinessRelationManagerDTO businessRelationManager) {
        this.businessRelationManager = businessRelationManager;
    }

    public Set<ProjectDTO> getSecondaryProjects() {
        return secondaryProjects;
    }

    public void setSecondaryProjects(Set<ProjectDTO> secondaryProjects) {
        this.secondaryProjects = secondaryProjects;
    }

    public Set<ProjectDTO> getPrimaryProjects() {
        return primaryProjects;
    }

    public void setPrimaryProjects(Set<ProjectDTO> primaryProjects) {
        this.primaryProjects = primaryProjects;
    }
}
