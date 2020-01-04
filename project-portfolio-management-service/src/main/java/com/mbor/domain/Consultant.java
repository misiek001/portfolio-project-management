package com.mbor.domain;

import com.mbor.domain.employeeinproject.IProjectManager;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Consultant extends Employee implements IProjectManager {

    @ManyToOne
    private Supervisor supervisor;

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
