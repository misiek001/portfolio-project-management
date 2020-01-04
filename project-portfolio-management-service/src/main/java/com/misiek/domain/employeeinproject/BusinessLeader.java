package com.misiek.domain.employeeinproject;

import com.misiek.domain.BusinessEmployee;
import com.misiek.domain.Project;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessLeader extends ProjectRole<BusinessEmployee> {

    @OneToMany
    @Fetch(value = FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
