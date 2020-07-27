package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

public class ConsultantDTO extends EmployeeDTO {

    public ConsultantDTO(){
        super.setEmployeeType(this.getClass().getSimpleName());
    }

    @JsonView(Views.EmployeeInternal.class)
    private SupervisorDTO supervisor;

    public SupervisorDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(SupervisorDTO supervisor) {
        this.supervisor = supervisor;
    }

}
