package com.misiek.controller;

import com.misiek.domain.Project;
import com.misiek.service.IService;
import com.misiek.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<Project> {

   private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public IService getService() {
        return projectService;
    }

}
