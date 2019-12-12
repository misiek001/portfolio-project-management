package com.misiek.domain.employeeinproject;

import com.misiek.domain.Project;
import com.misiek.domain.Supervisor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ResourceManager extends ProjectRole<Supervisor> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

}
