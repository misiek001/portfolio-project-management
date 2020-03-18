package com.mbor.domain.employeeinproject;

import com.mbor.domain.Employee;
import com.mbor.domain.Project;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SolutionArchitect extends ProjectRole<Employee> {

    @ManyToMany(mappedBy = "solutionArchitects")
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
