package com.misiek.service;

import com.misiek.dao.AbstractDao;
import com.misiek.dao.EmployeeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends RawService {

    private EmployeeDaoImpl employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public AbstractDao getDao() {
        return employeeDao;
    }
}
