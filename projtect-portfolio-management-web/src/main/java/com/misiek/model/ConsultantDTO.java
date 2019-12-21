package com.misiek.model;

import com.misiek.domain.Supervisor;

public class ConsultantDTO extends EmployeeDTO {

    private Supervisor supervisorDTO;

    public Supervisor getSupervisorDTO() {
        return supervisorDTO;
    }

    public void setSupervisorDTO(Supervisor supervisorDTO) {
        this.supervisorDTO = supervisorDTO;
    }
}
