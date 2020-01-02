package com.misiek.controller;

import com.misiek.domain.Employee;
import com.misiek.mapping.Mapper;
import com.misiek.service.IEmployeeService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController<T> extends RawController<Employee> {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService) {
        this.employeeService = iEmployeeService;
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
