package com.mbor.model;

import javax.validation.constraints.NotNull;

public class ProjectRoleDTO<T extends EmployeeDTO> extends IdDTO  {

    @NotNull
    T employee;

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

}
