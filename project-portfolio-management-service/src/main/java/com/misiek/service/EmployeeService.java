package com.misiek.service;

import com.misiek.dao.EmployeeDao;
import com.misiek.dao.IDao;
import com.misiek.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class EmployeeService extends RawService<Employee> implements IEmployeeService<Employee> {

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
