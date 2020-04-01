package com.mbor.model;

public class ProjectRoleDTO<T extends EmployeeDTO> extends IdDTO  {

    T employee;

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

}
