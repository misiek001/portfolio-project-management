package com.misiek.controller;

import com.misiek.domain.Project;
import com.misiek.mapping.Mapper;
import com.misiek.mapping.ProjectMapper;
import com.misiek.service.IService;
import com.misiek.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<Project> {

   private final ProjectService projectService;

   private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @Override
    public IService getService() {
        return projectService;
    }

    @Override
    public Mapper getMapper() {
        return null;
    }
}
