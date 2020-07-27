package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

import java.util.HashSet;
import java.util.Set;

public class BusinessRelationManagerDTO  extends EmployeeDTO {

    public BusinessRelationManagerDTO (){
        super.setEmployeeType(this.getClass().getSimpleName());
    }

    @JsonView(Views.EmployeeInternal.class)
    private Set<ProjectDTO> projects = new HashSet<>();

    @JsonView(Views.EmployeeInternal.class)
    private DirectorDTO director;

    @JsonView(Views.EmployeeInternal.class)
    private Set<BusinessUnitDTO> assignedBusinessUnits;

    public Set<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDTO> projects) {
        this.projects = projects;
    }

    public DirectorDTO getDirector() {
        return director;
    }

    public void setDirector(DirectorDTO director) {
        this.director = director;
    }

    public Set<BusinessUnitDTO> getAssignedBusinessUnits() {
        return assignedBusinessUnits;
    }

    public void setAssignedBusinessUnits(Set<BusinessUnitDTO> assignedBusinessUnits) {
        this.assignedBusinessUnits = assignedBusinessUnits;
    }
}
