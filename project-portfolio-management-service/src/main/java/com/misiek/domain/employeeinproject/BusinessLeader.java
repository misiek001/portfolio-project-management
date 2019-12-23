package com.misiek.domain.employeeinproject;

import com.misiek.domain.BusinessEmployee;
import com.misiek.domain.Project;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessLeader extends ProjectRole<BusinessEmployee> {

    @OneToMany
    private Set<Project> projects = new HashSet<>();

}
