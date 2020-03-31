package com.mbor.model;

import java.util.HashSet;
import java.util.Set;

public class SupervisorDTO extends EmployeeDTO {

    private Set<ConsultantDTO> employees = new HashSet<>();

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
