package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

public class ProjectRoleDTO<T extends EmployeeDTO> extends IdDTO  {

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private T employee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

}
