package com.misiek.controller;

import com.misiek.domain.Employee;
import com.misiek.mapping.Mapper;
import com.misiek.service.EmployeeService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController<T> extends RawController<Employee> {

    private final   EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public IService getService() {
        return employeeService;
    }

    @Override
    public Mapper getMapper() {
        return null;
    }
}
