package com.mbor.domain.employeeinproject;

import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.Project;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessLeader extends ProjectRole<BusinessEmployee> {

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    @Fetch(value = FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
