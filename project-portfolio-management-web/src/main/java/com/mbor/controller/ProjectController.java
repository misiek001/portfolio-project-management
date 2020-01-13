package com.mbor.controller;

import com.mbor.model.ProjectDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController {

    private final IProjectService projectService;

    private final IEmployeeService employeeService;

    public ProjectController(IProjectService projectService, IEmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ProjectCreatedDTO> save(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  projectService.save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.OK);
    }

    public  ResponseEntity<List<ProjectDTO>> findAll() {
        return super.findAll();
    }

    public ResponseEntity<ProjectDTO> find(Long id) {
        return super.find(id);
    }

    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public IProjectService getService() {
        return projectService;
    }

}
