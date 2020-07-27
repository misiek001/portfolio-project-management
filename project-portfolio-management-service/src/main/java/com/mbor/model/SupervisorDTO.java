package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

import java.util.HashSet;
import java.util.Set;

public class SupervisorDTO extends EmployeeDTO {

    public SupervisorDTO (){
        super.setEmployeeType(this.getClass().getSimpleName());
    }

    @JsonView(Views.EmployeeInternal.class)
    private Set<ConsultantDTO> employees = new HashSet<>();

    @JsonView(Views.EmployeeInternal.class)
    private DirectorDTO director;

    public Set<ConsultantDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<ConsultantDTO> employees) {
        this.employees = employees;
    }

    public DirectorDTO getDirector() {
        return director;
    }

    public void setDirector(DirectorDTO director) {
        this.director = director;
    }
}
