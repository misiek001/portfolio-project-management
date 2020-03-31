package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userName")
public  class EmployeeDTO extends IdDTO {

    private String firstName;

    private String lastName;

    private String userName;

    private BusinessUnitDTO businessUnit;

    private Set<ProjectRoleDTO> projectRoleSet = new HashSet<>();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
