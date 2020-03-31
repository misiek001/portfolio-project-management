package com.mbor.model;

import java.util.HashSet;
import java.util.Set;

public class DirectorDTO extends EmployeeDTO {

    private Set<SupervisorDTO> supervisors = new HashSet<>();

    private Set<BusinessRelationManagerDTO> businessRelationManagers = new HashSet<>();

    public Set<SupervisorDTO> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(Set<SupervisorDTO> supervisors) {
        this.supervisors = supervisors;
    }

    public Set<BusinessRelationManagerDTO> getBusinessRelationManagers() {
        return businessRelationManagers;
    }

    public void setBusinessRelationManagers(Set<BusinessRelationManagerDTO> businessRelationManagers) {
        this.businessRelationManagers = businessRelationManagers;
    }
}
