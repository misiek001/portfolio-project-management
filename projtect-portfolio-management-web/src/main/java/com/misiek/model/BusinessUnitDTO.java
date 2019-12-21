package com.misiek.model;

import com.misiek.domain.Employee;
import com.misiek.domain.Project;

import java.util.HashSet;
import java.util.Set;

public class BusinessUnitDTO {

    private Long id;

    private String name;

    private Set<Employee> employees = new HashSet<>();

    private Set<Project> projects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
