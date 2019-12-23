package com.misiek.controller;

import com.misiek.domain.Employee;
import com.misiek.mapping.DTOtoEntityMapper;
import com.misiek.mapping.EntityToDTOMapper;
import com.misiek.service.EmployeeService;
import com.misiek.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController<T> extends RawController<Employee> {

    private  EmployeeService employeeService;

    @Autowired
    public EmployeeController(DTOtoEntityMapper dtOtoEntityMapper, EntityToDTOMapper entityToDTOMapper, EmployeeService employeeService) {
        super(dtOtoEntityMapper, entityToDTOMapper);
        this.employeeService = employeeService;
    }

    @Override
    public IService getService() {
        return employeeService;
    }

}
