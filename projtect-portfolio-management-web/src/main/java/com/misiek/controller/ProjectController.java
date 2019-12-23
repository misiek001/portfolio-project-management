package com.misiek.controller;

import com.misiek.domain.Project;
import com.misiek.mapping.DTOtoEntityMapper;
import com.misiek.mapping.EntityToDTOMapper;
import com.misiek.service.IService;
import com.misiek.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<Project> {

   private final ProjectService projectService;

    public ProjectController(DTOtoEntityMapper dtOtoEntityMapper, EntityToDTOMapper entityToDTOMapper, ProjectService projectService) {
        super(dtOtoEntityMapper, entityToDTOMapper);
        this.projectService = projectService;
    }

    @Override
    public IService getService() {
        return projectService;
    }


}
