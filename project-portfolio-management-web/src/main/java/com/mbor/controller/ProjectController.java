package com.mbor.controller;

import com.mbor.model.ProjectDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController {

    private final IProjectService projectService;


    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create_project')")
    public ResponseEntity<ProjectCreatedDTO> save(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  getService().save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.CREATED);
    }

    public  ResponseEntity<List<ProjectDTO>> findAll() {
        return super.findAll();
    }

    public ResponseEntity<ProjectDTO> find(Long id) {
        return super.find(id);
    }

    @PostMapping(params = "search=true")
    public ResponseEntity<List<ProjectDTO>> findByCriteria(@RequestBody SearchProjectDTO searchProjectDTO){
        List<ProjectDTO> projects = getService().findByMultipleCriteria(searchProjectDTO);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public IProjectService getService() {
        return projectService;
    }

}
