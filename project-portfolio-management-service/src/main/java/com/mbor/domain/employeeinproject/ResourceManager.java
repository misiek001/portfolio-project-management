package com.mbor.domain.employeeinproject;

import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ResourceManager extends ProjectRole<Supervisor> {

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
