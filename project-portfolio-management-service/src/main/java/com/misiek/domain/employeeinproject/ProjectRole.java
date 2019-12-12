package com.misiek.domain.employeeinproject;

import com.misiek.domain.Employee;

import javax.persistence.*;

@Entity
public abstract class ProjectRole<IEmployee> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(targetEntity = Employee.class)
    IEmployee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(IEmployee employee) {
        this.employee = employee;
    }
}
