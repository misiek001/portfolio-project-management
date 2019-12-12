package com.misiek.domain;

import com.misiek.domain.employeeinproject.IProjectManager;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Supervisor extends Employee implements IProjectManager {

    @OneToMany
    private Set<Consultant> employees = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Director director;

    public Set<Consultant> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Consultant> employees) {
        this.employees = employees;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
