package com.mbor.service;

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

import java.util.List;

public interface IAPIProjectService extends IAPIService<ProjectCreatedDTO, ProjectCreationDTO, ProjectDTO>  {

    ProjectDTO updateProjectAspects(Long projectId, ProjectAspectLineDTO projectAspectLineDTO, Long consultantId);

    ProjectDTO addProjectEndDate(Long projectId, RealEndDateDTO realEndDateDTO, Long projectManagerId);

    List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO);

    List<ProjectDTO> findResourceManagerProjects(Long resourceManagerId, ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO);

    List<ProjectDTO> findSupervisorProjects(Long supervisorId, SupervisorSearchProjectDTO supervisorSearchProjectDTO);

    List<ProjectDTO> findConsultantProjects(Long consultantId);

    ProjectDTO assignEmployee(Long projectId, EmployeeAssignDTO employeeAssignDTO);

    ProjectDTO openProject(long projectId, OpenProjectDTO openProjectDTO);
}
