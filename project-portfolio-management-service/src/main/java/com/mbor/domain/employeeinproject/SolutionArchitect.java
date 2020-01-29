package com.mbor.domain.employeeinproject;

import com.mbor.domain.Employee;
import com.mbor.domain.Project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SolutionArchitect extends ProjectRole<Employee> {

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    private Set<Project> projects = new HashSet<>();

}
