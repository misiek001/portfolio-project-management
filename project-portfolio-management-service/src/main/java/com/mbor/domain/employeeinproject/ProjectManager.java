package com.mbor.domain.employeeinproject;

import com.mbor.domain.Project;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProjectManager extends ProjectRole<IProjectManager> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

}
