package com.misiek.dao;

import com.misiek.domain.Employee;

import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> save(Employee project);

    Optional<Employee> find(Long id);

}
