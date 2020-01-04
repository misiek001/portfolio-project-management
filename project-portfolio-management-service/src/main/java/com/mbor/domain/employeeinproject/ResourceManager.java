package com.mbor.domain.employeeinproject;

import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ResourceManager extends ProjectRole<Supervisor> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

}
