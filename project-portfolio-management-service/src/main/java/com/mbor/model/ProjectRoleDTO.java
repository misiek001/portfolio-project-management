package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.validation.constraints.NotNull;

public class ProjectRoleDTO<T extends EmployeeDTO> extends IdDTO  {

    @NotNull
    @JsonManagedReference
    T employee;

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

}
