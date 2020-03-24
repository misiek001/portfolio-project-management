package com.mbor.controller;

import com.mbor.domain.Consultant;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.model.ProjectDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.ProjectAspectLineDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.model.search.SupervisorSearchProjectDTO;
import com.mbor.service.IEmployeeService;
import com.mbor.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('create_project')")
    public ResponseEntity<ProjectCreatedDTO> createProject(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  getService().save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('assign_employee')")
    public ResponseEntity<ProjectDTO> assignEmployeeToProject(@RequestBody EmployeeAssignDTO employeeAssignDTO){
        ProjectDTO assignedProject = projectService.assignEmployee(employeeAssignDTO);
        return new ResponseEntity<>(assignedProject, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("hasAuthority('add_project_aspects_line')")
    public ResponseEntity<ProjectDTO> addProjectAspectsLine(@PathVariable Long projectId, @RequestBody ProjectAspectLineDTO projectAspectLineDTO, final Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        Long projectManagerId = employeeService.getDemandedProjectRoleId(ProjectRole.class, principal);
        ProjectDTO projectDTO = projectService.updateProjectAspects(projectId, projectAspectLineDTO, projectManagerId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping(params = "search=true")
    public ResponseEntity<List<ProjectDTO>> findByCriteria(@RequestBody SearchProjectDTO searchProjectDTO){
        List<ProjectDTO> projects = getService().findByMultipleCriteria(searchProjectDTO);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping(params = "searchingEmployee=resource-manager")
    @PreAuthorize("hasAuthority('search_resource_manager_projects')")
    public ResponseEntity<List<ProjectDTO>> findResourceManagerProjects(@RequestBody ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO, final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long resourceManagerId = employeeService.getDemandedProjectRoleId(ResourceManager.class, principal);
        List<ProjectDTO> projects = getService().findResourceManagerProjects(resourceManagerId, resourceManagerSearchProjectDTO);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @PostMapping(params = "searchingEmployee=supervisor")
    @PreAuthorize("hasAuthority('search_supervisor_projects')")
    public ResponseEntity<List<ProjectDTO>> findSupervisorProjects(@RequestBody SupervisorSearchProjectDTO supervisorSearchProjectDTO, final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long supervisorId = employeeService.getDemandedEmployeeId(Supervisor.class, principal);
        List<ProjectDTO> projects = getService().findSupervisorProjects(supervisorId, supervisorSearchProjectDTO);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping(params = "searchingEmployee=consultant")
    @PreAuthorize("hasAuthority('search_consultant_projects')")
    public ResponseEntity<List<ProjectDTO>> findConsultantProjects(final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long consultantId = employeeService.getDemandedEmployeeId(Consultant.class, principal);
        List<ProjectDTO> projects = getService().findConsultantProjects(consultantId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @Override
    public IProjectService getService() {
        return projectService;
    }

}
