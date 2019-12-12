package com.misiek.dao;

import com.misiek.domain.Employee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl extends RawDao<Employee> {

    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Employee.class;
    }
}
