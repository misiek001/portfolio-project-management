package com.mbor.service;

import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.domain.employeeinproject.ProjectManager;
import com.mbor.domain.employeeinproject.ResourceManager;
import com.mbor.domain.employeeinproject.SolutionArchitect;
import com.mbor.domain.projectaspect.ProjectAspectLine;
import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.domain.search.SearchProject;
import com.mbor.domain.search.SupervisorSearchProject;
import com.mbor.exception.NoSetProjectManagerException;
import com.mbor.exception.ProjectCannotBeOpenedException;
import com.mbor.exception.WrongProjectManagerException;
import com.mbor.mapper.project.ProjectAspectLineMapper;
import com.mbor.mapper.project.ProjectMapper;
import com.mbor.mapper.project.RealEndDateMapper;
import com.mbor.mapper.search.ResourceManagerSearchProjectsMapper;
import com.mbor.mapper.search.SearchProjectsMapper;
import com.mbor.mapper.search.SupervisorSearchProjectMapper;
import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.RealEndDateDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.projectaspect.ProjectAspectLineDTO;
import com.mbor.model.projectworkflow.OpenProjectDTO;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import com.mbor.model.search.SearchProjectDTO;
import com.mbor.model.search.SupervisorSearchProjectDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.mbor.service.ServiceUtils.tryCast;

@Service
@Transactional
public class ProjectService extends RawService<Project> implements IProjectService {

    private final IProjectDao projectDao;

    private final IInternalEmployeeService employeeService;
    private final IInternalBusinessUnitService businessUnitService;
    private final IInternalProjectRoleService projectRoleService;

    private final ProjectMapper projectMapper;
    private final ProjectAspectLineMapper projectAspectMapper;
    private final RealEndDateMapper realEndDateMapper;
    private final SearchProjectsMapper searchProjectsMapper;
    private final ResourceManagerSearchProjectsMapper resourceManagerSearchProjectsMapper;
    private final SupervisorSearchProjectMapper supervisorSearchProjectMapper;

    public ProjectService(IProjectDao projectDao, IInternalEmployeeService employeeService, IInternalBusinessUnitService businessUnitService, IInternalProjectRoleService projectRoleService, ProjectMapper projectMapper, ProjectAspectLineMapper projectAspectMapper, RealEndDateMapper realEndDateMapper, SearchProjectsMapper searchProjectsMapper, ResourceManagerSearchProjectsMapper resourceManagerSearchProjectsMapper, SupervisorSearchProjectMapper supervisorSearchProjectMapper) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
        this.projectRoleService = projectRoleService;
        this.projectMapper = projectMapper;
        this.projectAspectMapper = projectAspectMapper;
        this.realEndDateMapper = realEndDateMapper;
        this.searchProjectsMapper = searchProjectsMapper;
        this.resourceManagerSearchProjectsMapper = resourceManagerSearchProjectsMapper;
        this.supervisorSearchProjectMapper = supervisorSearchProjectMapper;
    }

    @Override
    public ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO) {
        throw new RuntimeException("Method not supported");
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> projects =  super.findAllInternal();
        return convertResultsToDTO(projects);
    }

    @Override
    public ProjectDTO find(Long id) {
        return projectMapper.convertEntityToDto(findInternal(id));
    }

    @Override
    public List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO) {
        SearchProject searchProject = searchProjectsMapper.convertDtoToEntity(searchProjectDTO);
        List<Project> foundProjects = getDao().findByMultipleCriteria(searchProject);
        return convertResultsToDTO(foundProjects);
    }

    private List<ProjectDTO> convertResultsToDTO(List<Project> foundProjects){
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        foundProjects.forEach(project -> projectDTOList.add(projectMapper.convertEntityToDto(project)));
        return projectDTOList;
    }

    @Override
    public List<ProjectDTO> findResourceManagerProjects(Long resourceManagerId, ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO){
        ResourceManagerSearchProject resourceManagerSearchProject = resourceManagerSearchProjectsMapper.convertDtoToEntity(resourceManagerSearchProjectDTO);
        List<Project> foundProjects = getDao().findResourceManagerProjects(resourceManagerId, resourceManagerSearchProject);
        return convertResultsToDTO(foundProjects);
    }

    @Override
    public List<ProjectDTO> findSupervisorProjects(Long supervisorId, SupervisorSearchProjectDTO supervisorSearchProjectDTO){
        SupervisorSearchProject supervisorSearchProject = supervisorSearchProjectMapper.convertDtoToEntity(supervisorSearchProjectDTO);
        List<Project> foundProjects = getDao().findSupervisorProjects(supervisorId, supervisorSearchProject);
        return convertResultsToDTO(foundProjects);
    }


    @Override
    public List<ProjectDTO> findConsultantProjects(Long consultantId){
        List<Project> foundProjects = getDao().findConsultantProjects(consultantId);
        return convertResultsToDTO(foundProjects);
    }


    @Override
    public ProjectDTO openProject(long projectId, OpenProjectDTO openProjectDTO) {
        Project project = findInternal(projectId);

        validateProjectIfCanByOpen(project);

        project.setResourceManager((ResourceManager) projectRoleService.findInternal(openProjectDTO.getResourceManagerId()));
        project.setProjectManager((ProjectManager) projectRoleService.findInternal(openProjectDTO.getProjectManagerId()));
        openProjectDTO.getSolutionArchitects().forEach( id -> {
            project.addSolutionArchitect((SolutionArchitect) projectRoleService.findInternal(id));
        });

        ProjectStatusHistoryLine projectStatusHistoryLine = prepareProjectStatusHistoryLine(ProjectStatus.ANALYSIS, ProjectStatus.AWAITING, "Project awaiting to start");
        project.addProjectStatusHistoryLine(projectStatusHistoryLine);
        updateInternal(project);
        return projectMapper.convertEntityToDto(project);
    }

    @Override
    public void rejectProject(long projectId) {
        Project project = findInternal(projectId);
        ProjectStatusHistoryLine projectStatusHistoryLine = prepareProjectStatusHistoryLine(ProjectStatus.ANALYSIS, ProjectStatus.REJECTED, "Project rejected");
        project.addProjectStatusHistoryLine(projectStatusHistoryLine);
    }

    @Override
    public ProjectDTO updateProjectAspects(Long projectId, ProjectAspectLineDTO projectAspectLineDTO, Long projectManagerId) {
        Project project = getProjectDedicatedToProjectManager(projectId, projectManagerId);
        ProjectAspectLine projectAspectLine = projectAspectMapper.convertDtoToEntity(projectAspectLineDTO);
        project.addProjectAspectLine(projectAspectLine);
        return projectMapper.convertEntityToDto(updateInternal(project));
    }

    @Override
    public void delete(Long id) {
        Project project = findInternal(id);
        if (project.getProjectManager() != null) {
            project.getProjectManager().getProjects().remove(project);
        }
        if(project.getResourceManager() != null){
            project.getResourceManager().getProjects().remove(project);
        }
        if(project.getBusinessRelationManager() != null){
            project.getBusinessRelationManager().getProjects().remove(project);
        }
        project.getSolutionArchitects().forEach(solutionArchitect -> solutionArchitect
                .getProjects()
                .remove(project));
        updateInternal(project);
        deleteInternal(id);
    }

    private ProjectStatusHistoryLine prepareProjectStatusHistoryLine(ProjectStatus previousStatus, ProjectStatus currentStatus, String description) {
        ProjectStatusHistoryLine projectStatusHistoryLine = new ProjectStatusHistoryLine();
        projectStatusHistoryLine.setPreviousStatus(previousStatus);
        projectStatusHistoryLine.setCurrentStatus(currentStatus);
        projectStatusHistoryLine.setDescription(description);
        return projectStatusHistoryLine;
    }

    private void validateProjectIfCanByOpen(Project project) {
        if(project.getResourceManager() != null){
            throw new ProjectCannotBeOpenedException("Project has already assigned RM with ID " + project.getResourceManager().getId());
        }
        if(project.getProjectManager() != null){
            throw new ProjectCannotBeOpenedException("Project has already assigned PM with ID " + project.getProjectManager().getId());
        }
        if(!project.getSolutionArchitects().isEmpty()){
            throw new ProjectCannotBeOpenedException("Project has already assigned SA");
        }
        if(project.getRecentStatus().isPresent()){
            ProjectStatusHistoryLine projectStatusHistoryLine = project.getRecentStatus().get();
            if(!projectStatusHistoryLine.getCurrentStatus().equals(ProjectStatus.ANALYSIS)){
                throw new ProjectCannotBeOpenedException("Project is not in ANALYSIS status, but: " + projectStatusHistoryLine.getCurrentStatus());
            }
        }
    }

    @Override
    public ProjectDTO addProjectEndDate(Long projectId, RealEndDateDTO realEndDateDTO, Long projectManagerId){
        Project project = getProjectDedicatedToProjectManager(projectId, projectManagerId);
        RealEndDate realEndDate = realEndDateMapper.convertDtoToEntity(realEndDateDTO);
        project.addRealEndDate(realEndDate);

    return projectMapper.convertEntityToDto(updateInternal(project));
    }

    private Project getProjectDedicatedToProjectManager(Long projectId, Long projectManagerId) {
        Project project = findInternal(projectId);
        if (project.getProjectManager() == null) {
            throw new NoSetProjectManagerException("No Project Manager assigned to project with id:" + project.getId());
        }
        if (!project.getProjectManager().getId().equals(projectManagerId)) {
            throw new WrongProjectManagerException("Project with id:" + project.getId() + " has projectManagerWith id: " + project.getProjectManager().getId() + " ,not:" + projectManagerId);
        }
        return project;
    }


    @Override
    public ProjectDTO assignEmployee(Long projectId, EmployeeAssignDTO employeeAssignDTO) {
        Project project = findInternal(projectId);
        if (employeeAssignDTO.getProjectManagerId() != null) {
            ProjectManager  projectManager = tryCast(ProjectManager.class, projectRoleService.findInternal(employeeAssignDTO.getProjectManagerId()));
            project.setProjectManager(projectManager);
            }
        if (employeeAssignDTO.getBusinessRelationManagerId() != null) {
            project.setBusinessRelationManager(tryCast(BusinessRelationManager.class, employeeService.findInternal(employeeAssignDTO.getBusinessRelationManagerId())));
        }
        if (employeeAssignDTO.getResourceManagerId() != null) {
            ResourceManager resourceManager = tryCast(ResourceManager.class, projectRoleService.findInternal(employeeAssignDTO.getResourceManagerId()));
            project.setResourceManager(resourceManager);
        }
        if (!employeeAssignDTO.getSolutionArchitectIdSet().isEmpty()) {
            employeeAssignDTO.getSolutionArchitectIdSet().forEach(solutionArchitectId -> {
                project.addSolutionArchitect(tryCast(SolutionArchitect.class, projectRoleService.findInternal(solutionArchitectId)));
            });
        }
        if (employeeAssignDTO.getBusinessLeaderId() != null) {
            BusinessLeader   businessLeader = tryCast(BusinessLeader.class, projectRoleService.findInternal(employeeAssignDTO.getBusinessLeaderId()));
            project.setBusinessLeader(businessLeader);
        }
        return projectMapper.convertEntityToDto(updateInternal(project));
    }

    private Function<ProjectClassDTO, ProjectClass> mapProjectClassDTOToProjectClass() {
        return projectClassDTO -> Enum.valueOf(ProjectClass.class, projectClassDTO.name());
    }

    private Function<ProjectStatusDTO, ProjectStatus> mapProjectStatusDTOToProjectStatus() {
        return projectStatusDTO -> Enum.valueOf(ProjectStatus.class, projectStatusDTO.name());
    }

    @Override
    public IProjectDao getDao() {
        return projectDao;
    }
}

