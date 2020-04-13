package com.mbor.domain.employeeinproject;

import com.mbor.domain.Employee;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProjectRole< T extends IEmployee> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true)
    Long id;

    @ManyToOne(targetEntity = Employee.class)
    T employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        employee.getProjectRoleSet().add(this);
        this.employee = employee;
    }


}
