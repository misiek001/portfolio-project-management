package com.mbor.dao;

import com.mbor.domain.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao extends RawDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        this.clazz = Employee.class;
    }

}
