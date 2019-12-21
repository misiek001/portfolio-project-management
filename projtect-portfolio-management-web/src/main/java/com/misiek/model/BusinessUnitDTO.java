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

}
