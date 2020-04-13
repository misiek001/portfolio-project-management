package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class ProjectRoleDTO<T extends EmployeeDTO> extends IdDTO  {

    @JsonView(Views.ProjectRoleInternal.class)
    T employee;

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

}
