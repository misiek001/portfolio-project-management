package com.mbor.domain.employeeinproject;

import com.mbor.domain.BusinessUnit;

import java.util.Set;

public interface IEmployee {

    Long getId();

    String getFirstName();

    String getLastName();

    BusinessUnit getBusinessUnit();

    Set<ProjectRole> getProjectRoleSet();
}
