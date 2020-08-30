package com.mbor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;
import com.mbor.model.views.Views;
import com.mbor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ProjectRequests")
public class ProjectRequestController extends RawController {

    private final IAPIProjectRequestService ProjectRequestService;
    private final IAPIEmployeeService employeeService;

    @Autowired
    public ProjectRequestController(IAPIProjectRequestService ProjectRequestService, IAPIEmployeeService employeeService) {
        this.ProjectRequestService = ProjectRequestService;
        this.employeeService = employeeService;
    }

    @PostMapping
    @JsonView(Views.Public.class)
    @PreAuthorize("hasAuthority('create_ProjectRequest')")
    public ResponseEntity<com.mbor.model.creation.ProjectRequestDTO> createProjectRequest(@RequestBody ProjectRequestCreationDTO projectRequestCreationDTO){
        com.mbor.model.creation.ProjectRequestDTO projectRequestDTO =  getService().save(projectRequestCreationDTO);
        return new ResponseEntity<>(projectRequestDTO, HttpStatus.CREATED);
    }

    @GetMapping(params = "searchingProjectRequests=ofBrmNoProject")
    @JsonView(Views.Public.class)
    @PreAuthorize("hasAuthority('get_all_ProjectRequests_of_brm_with_no_projects')")
    public ResponseEntity<List<ProjectRequestDTO>> createProjectRequest(final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long brmId = employeeService.getDemandedEmployeeId(BusinessRelationManager.class, principal);
        return new ResponseEntity(getService().findAllProjectRequestsOfBRMWithNoProject(brmId), HttpStatus.OK);
    }

    @Override
    public IAPIProjectRequestService getService() {
        return ProjectRequestService;
    }
}
