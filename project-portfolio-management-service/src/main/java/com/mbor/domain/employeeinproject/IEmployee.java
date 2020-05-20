package com.mbor.domain.employeeinproject;

import com.mbor.domain.BusinessUnit;

import java.util.Set;

public interface IEmployee {

    Long getId();

    String getFirstName();

    String getLastName();

    String getUserName();

    BusinessUnit getBusinessUnit();

    Set<ProjectRole> getProjectRoleSet();
}
