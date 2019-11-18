package com.misiek.dao;

import com.misiek.domain.Employee;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceContext;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    @PersistenceContext
    SessionFactory sessionFactory;

    @Override
    public Optional<Employee> save(Employee project) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> find(Long id) {
        return Optional.empty();
    }
}
