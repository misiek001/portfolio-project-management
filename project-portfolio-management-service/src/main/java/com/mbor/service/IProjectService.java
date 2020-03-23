package com.mbor.service;

import com.mbor.model.ProjectDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.ProjectAspectLineDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.model.search.SupervisorSearchProjectDTO;

import java.util.List;

public interface IProjectService<T> extends IService<T> {

     ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO);

    ProjectDTO updateProjectAspects(Long projectId, ProjectAspectLineDTO projectAspectLineDTO);

    List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO);

     List<ProjectDTO> findResourceManagerProjects(Long resourceManagerId, ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO);

     List<ProjectDTO> findSupervisorProjects(Long supervisorId, SupervisorSearchProjectDTO supervisorSearchProjectDTO);

     List<ProjectDTO> findConsultantProjects(Long consultantId);

     ProjectDTO assignEmployee(EmployeeAssignDTO employeeAssignDTO);
}
