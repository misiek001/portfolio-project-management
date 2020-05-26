package com.mbor.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.model.views.Views;

import java.util.List;

public class ProjectRolesDTO {

    @JsonView(Views.Public.class)
    private List<ProjectRoleDTO> projectRoles;

    public List<ProjectRoleDTO> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(List<ProjectRoleDTO> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
