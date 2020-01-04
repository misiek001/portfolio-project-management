package com.mbor.dao;

import com.mbor.domain.Employee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao extends RawDao<Employee> implements IEmployeeDao {

    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = Employee.class;
    }

}
