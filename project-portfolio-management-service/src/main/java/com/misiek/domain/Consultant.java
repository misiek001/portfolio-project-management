package com.misiek.domain;

import com.misiek.domain.employeeinproject.IProjectManager;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Consultant extends Employee implements IProjectManager {

    @ManyToOne
    private Supervisor supervisor;

}
