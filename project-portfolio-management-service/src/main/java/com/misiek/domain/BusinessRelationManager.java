package com.misiek.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessRelationManager extends Employee {

    @OneToMany(mappedBy = "businessRelationManager")
    @Fetch(value = FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Fetch(value = FetchMode.JOIN)
    private Director director;

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
