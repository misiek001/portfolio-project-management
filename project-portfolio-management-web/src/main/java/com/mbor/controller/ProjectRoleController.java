package com.mbor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.model.ProjectRoleDTO;
import com.mbor.model.ProjectRolesDTO;
import com.mbor.model.views.Views;
import com.mbor.service.IAPIProjectRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/projectRoles")
public class ProjectRoleController extends RawController<ProjectRoleDTO, ProjectRole> {

    private final IAPIProjectRoleService projectRoleService;

    public ProjectRoleController(IAPIProjectRoleService projectRoleService) {
        this.projectRoleService = projectRoleService;
    }

    @GetMapping(params = "action=getAllSpecificProjectRoles")
    @JsonView(Views.Public.class)
    public ResponseEntity<ProjectRolesDTO> findAllDemandedRole(@RequestParam String projectRole){
        Class projectRoleClass = projectRoleService.returnProjectRoleClass(projectRole);
        ProjectRolesDTO result = projectRoleService.findAllDemandRole(projectRoleClass);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public IAPIProjectRoleService getService() {
        return projectRoleService;
    }
}
