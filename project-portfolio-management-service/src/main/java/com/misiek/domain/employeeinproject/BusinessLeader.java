package com.misiek.domain.employeeinproject;

import com.misiek.domain.Employee;
import com.misiek.domain.Project;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessLeader extends ProjectRole<Employee> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

}
