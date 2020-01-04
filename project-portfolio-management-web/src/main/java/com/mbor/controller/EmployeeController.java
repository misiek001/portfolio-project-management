package com.mbor.controller;

import com.mbor.service.IEmployeeService;
import com.mbor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController<T> extends RawController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService) {
        this.employeeService = iEmployeeService;
    }

    @Override
    public IService getService() {
        return employeeService;
    }

}
