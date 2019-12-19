package com.misiek.controller;

import com.misiek.domain.Employee;
import com.misiek.service.EmployeeService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController<T> extends RawController<Employee> {

    private  EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public IService getService() {
        return employeeService;
    }
}
