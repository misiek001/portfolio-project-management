package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

import java.util.HashSet;
import java.util.Set;

public class DirectorDTO extends EmployeeDTO {

    public DirectorDTO (){
        super.setEmployeeType(this.getClass().getSimpleName());
    }

    @JsonView(Views.EmployeeInternal.class)
    private Set<SupervisorDTO> supervisors = new HashSet<>();

    @JsonView(Views.EmployeeInternal.class)
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
