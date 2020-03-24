package com.mbor.domain.employeeinproject;

import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ResourceManager extends ProjectRole<Supervisor> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @PreUpdate
    private void postUpdate() {
        System.out.println();
    }

}
