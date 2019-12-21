package com.misiek.model;

import java.util.Set;

public abstract class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private BusinessUnitDTO businessUnit;

    private Set<ProjectRoleDTO> projectRoleSet;

}
