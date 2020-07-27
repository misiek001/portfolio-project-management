package com.mbor.model;

public class BusinessEmployeeDTO extends EmployeeDTO {

    public BusinessEmployeeDTO(){
        super.setEmployeeType(this.getClass().getSimpleName());
    }
}
