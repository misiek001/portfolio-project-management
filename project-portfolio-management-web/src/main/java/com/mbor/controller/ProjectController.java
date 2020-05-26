package com.mbor.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mbor.domain.Consultant;
import com.mbor.domain.Project;
import com.mbor.domain.Supervisor;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.model.ProjectDTO;
import com.mbor.model.RealEndDateDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.ProjectAspectLineDTO;
import com.mbor.model.projectworkflow.OpenProjectDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.model.search.SupervisorSearchProjectDTO;
import com.mbor.model.views.Views;
import com.mbor.service.IAPIProjectService;
import com.mbor.service.IEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends RawController<ProjectDTO, Project> {

    private final IAPIProjectService projectService;

    private final IEmployeeService employeeService;

    public ProjectController(IAPIProjectService projectService, IEmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping(params = "searchingEmployee=consultant")
    @PreAuthorize("hasAuthority('search_consultant_projects')")
    public ResponseEntity<List<ProjectDTO>> findConsultantProjects(final Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        Long consultantId = employeeService.getDemandedEmployeeId(Consultant.class, principal);
        List<ProjectDTO> projects = getService().findConsultantProjects(consultantId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create_project')")
    public ResponseEntity<ProjectCreatedDTO> createProject(@RequestBody ProjectCreationDTO projectCreationDTO){
        ProjectCreatedDTO projectCreatedDTO =  getService().save(projectCreationDTO);
        return new ResponseEntity<>(projectCreatedDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{projectId}")
    @JsonView(Views.ProjectInternal.class)
    @PreAuthorize("hasAuthority('open_project')")
    public ResponseEntity<ProjectDTO> openProject(@PathVariable long projectId, @RequestBody OpenProjectDTO openProjectDTO){
        ProjectDTO openedProject = projectService.openProject(projectId, openProjectDTO);
        return new ResponseEntity<>(openedProject, HttpStatus.OK);
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

    @PatchMapping(value = "/{projectId}", params = "action=assign-employee")
    @PreAuthorize("hasAuthority('assign_employee')")
    public ResponseEntity<ProjectDTO> assignEmployeeToProject(@PathVariable Long projectId, @RequestBody EmployeeAssignDTO employeeAssignDTO){
        ProjectDTO assignedProject = projectService.assignEmployee(projectId, employeeAssignDTO);
        return new ResponseEntity<>(assignedProject, HttpStatus.OK);
    }

    @PatchMapping(value = "/{projectId}", params = "action=add-real-end-date")
    @PreAuthorize("hasAuthority('add_real_end_date')")
    public ResponseEntity<ProjectDTO> addRealEndDateToProject(@PathVariable Long projectId, @RequestBody RealEndDateDTO realEndDateDTO, final  Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        Long projectManagerId = employeeService.getDemandedProjectRoleId(ProjectManager.class, principal);
        ProjectDTO projectDTO = projectService.addProjectEndDate(projectId, realEndDateDTO, projectManagerId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PatchMapping(value = "/{projectId}", params = "action=add-project-aspect-line")
    @PreAuthorize("hasAuthority('add_project_aspects_line')")
    public ResponseEntity<ProjectDTO> addProjectAspectsLine(@PathVariable Long projectId, @RequestBody ProjectAspectLineDTO projectAspectLineDTO, final Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        Long projectManagerId = employeeService.getDemandedProjectRoleId(ProjectManager.class, principal);
        ProjectDTO projectDTO = projectService.updateProjectAspects(projectId, projectAspectLineDTO, projectManagerId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @Override
    public IAPIProjectService getService() {
        return projectService;
    }

}
