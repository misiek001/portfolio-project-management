package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.EmployeeDao;
import com.misiek.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends RawService<Employee> {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public IDao getDao() {
        return employeeDao;
    }
}
