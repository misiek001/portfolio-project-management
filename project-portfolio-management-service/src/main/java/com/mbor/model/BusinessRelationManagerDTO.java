package com.mbor.model;

import java.util.HashSet;
import java.util.Set;

public class BusinessRelationManagerDTO  extends EmployeeDTO {

    private Set<ProjectDTO> projects = new HashSet<>();

    private DirectorDTO director;

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
}
