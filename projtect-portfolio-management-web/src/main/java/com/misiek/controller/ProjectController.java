package com.misiek.controller;

import com.misiek.domain.*;
import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.domain.employeeinproject.ProjectManager;
import com.misiek.domain.employeeinproject.ResourceManager;
import com.misiek.domain.employeeinproject.SolutionArchitect;
import com.misiek.mapping.Mapper;
import com.misiek.model.ProjectDTO;
import com.misiek.service.IService;
import com.misiek.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<Project> {

   private ProjectService projectService;

   private Mapper mapper;

    public ProjectController(ProjectService projectService, Mapper mapper) {
        this.projectService = projectService;
        this.mapper = mapper;
    }

    @Override
    public IService getService() {
        return projectService;
    }

    @GetMapping("/test")
    public ResponseEntity<ProjectDTO>  test(){
        Project project = new Project();
        project.setId(1l);
        project.setName("Random Project Name");
        Director director = new Director();
        director.setId(4L);
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.setId(2l);
        resourceManager.setEmployee(new Supervisor());
        resourceManager.getEmployee().setId(5l);
        resourceManager.getEmployee().setDirector(director);
        project.setResourceManager(resourceManager);
        ProjectManager projectManager = new ProjectManager();
        project.setProjectManager(projectManager);
        BusinessRelationManager businessRelationManager = new BusinessRelationManager();
        businessRelationManager.setId(3L);
        businessRelationManager.setDirector(director);
        project.setBusinessRelationManager(businessRelationManager);
        BusinessLeader businessLeader = new BusinessLeader();
        businessLeader.setId(5L);
        project.setBusinessUnitLeader(businessLeader);
        SolutionArchitect solutionArchitect1 = new SolutionArchitect();
        solutionArchitect1.setId(6l);
        SolutionArchitect solutionArchitect2 = new SolutionArchitect();
        solutionArchitect2.setId(7l);
        Set<SolutionArchitect> set = new HashSet<>();
        set.add(solutionArchitect1);
        set.add(solutionArchitect2);
        project.setSolutionArchitect(set);
        project.setStatus(ProjectStatus.ANALYSIS);
        project.setStartDate(LocalDateTime.now());
        project.setPlannedEndDate(LocalDateTime.of(2020,11,11,1,1));
        BusinessUnit businessUnit1 = new BusinessUnit();
        businessUnit1.setId(8L);
        Set<BusinessUnit> businessUnits = new HashSet<>();
        businessUnits.add(businessUnit1);
        project.setBusinessUnits(businessUnits);

        return new ResponseEntity<>(mapper.mapProjectEntityToDTO(project), HttpStatus.OK);
    }

}
