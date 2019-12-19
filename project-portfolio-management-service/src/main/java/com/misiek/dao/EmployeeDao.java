package com.misiek.dao;

import com.misiek.domain.Employee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao extends RawDao<Employee> {

    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Employee.class;
    }
}
