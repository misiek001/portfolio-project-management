package com.misiek.model;

import java.util.Set;

public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private BusinessUnitDTO businessUnit;

    private Set<ProjectRoleDTO> projectRoleSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BusinessUnitDTO getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnitDTO businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Set<ProjectRoleDTO> getProjectRoleSet() {
        return projectRoleSet;
    }

    public void setProjectRoleSet(Set<ProjectRoleDTO> projectRoleSet) {
        this.projectRoleSet = projectRoleSet;
    }
}
