package com.mbor.model;

public class ConsultantDTO extends EmployeeDTO {

    private SupervisorDTO supervisor;

    public SupervisorDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(SupervisorDTO supervisor) {
        this.supervisor = supervisor;
    }
}
