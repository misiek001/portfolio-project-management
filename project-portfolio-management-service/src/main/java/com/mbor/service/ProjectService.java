package com.mbor.service;

import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.*;
import com.mbor.exception.ProjectRoleAlreadyExist;
import com.mbor.exception.WrongEmployeeTypeException;
import com.mbor.mapper.ProjectMapper;
import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.SolutionArchitectDTO;
import com.mbor.model.assignment.EmployeeAssignDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.search.SearchProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService extends RawService<Project> implements IProjectService<Project> {

    private final IProjectDao projectDao;

    private final IEmployeeService employeeService;

    private final IBusinessUnitService businessUnitService;

    private final IProjectRoleService projectRoleService;

    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(IProjectDao projectDao, IEmployeeService employeeService, IBusinessUnitService businessUnitService, IProjectRoleService projectRoleService, ProjectMapper projectMapper) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
        this.projectRoleService = projectRoleService;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectCreatedDTO save(ProjectCreationDTO projectCreationDTO) {
        Project project = projectMapper.convertCreationDtoToEntity(projectCreationDTO);

        Project savedProject = saveInternal(project);
        return projectMapper.convertEntityToCreatedDto(savedProject);
    }

    @Override
    public List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO) {
        String projectName = searchProjectDTO.getProjectName();
        List<ProjectClass> projectClass = null;
        if (searchProjectDTO.getProjectClassDTOList() != null) {
            projectClass = searchProjectDTO.getProjectClassDTOList()
                    .stream()
                    .map(mapProjectClassDTOToProjectClass())
                    .collect(Collectors.toList());
        }
        String businessUnitName = searchProjectDTO.getBusinessUnitName();
        List<ProjectStatus> projectStatusList = null;
        if (searchProjectDTO.getProjectStatusDTOList() != null) {
            projectStatusList = searchProjectDTO.getProjectStatusDTOList()
                    .stream()
                    .map(mapProjectStatusDTOToProjectStatus())
                    .collect(Collectors.toList());
        }
        LocalDate projectStartDate = searchProjectDTO.getProjectStartDateLaterThat();
        System.out.println(businessUnitService.find(1l));
        List<Project> foundProject = getDao().findByMultipleCriteria(projectName, projectClass, businessUnitName, projectStatusList, projectStartDate);
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        foundProject.forEach(project -> projectDTOList.add(projectMapper.convertToDto(project)));
        return projectDTOList;
    }

    @Override
    public ProjectDTO assignEmployee(EmployeeAssignDTO employeeAssignDTO) {
        Project project = find(employeeAssignDTO.getProjectId());
        if (employeeAssignDTO.getProjectManagerDTO() != null) {
            ProjectManager projectManager;
            if (employeeAssignDTO.getProjectManagerDTO().getId() != null) {
                projectManager = tryCast(ProjectManager.class, projectRoleService.find(employeeAssignDTO.getProjectManagerDTO().getId()));
            } else {
                Long employeeId = employeeAssignDTO.getProjectManagerDTO().getEmployee().getId();
                checkIfRoleAlreadyExist(ProjectManager.class,employeeId );
                projectManager = new ProjectManager();
                projectManager.setEmployee(tryCast(IProjectManager.class, employeeService.find(employeeId)));
            }
            project.setProjectManager(projectManager);
        }
        if (employeeAssignDTO.getBusinessRelationManagerDTO() != null) {
            project.setBusinessRelationManager(tryCast(BusinessRelationManager.class, employeeService.find(employeeAssignDTO.getBusinessRelationManagerDTO().getId())));
        }
        if (employeeAssignDTO.getResourceManagerDTO() != null) {
            ResourceManager resourceManager;
            if (employeeAssignDTO.getResourceManagerDTO().getId() != null) {
                resourceManager = tryCast(ResourceManager.class, projectRoleService.find(employeeAssignDTO.getResourceManagerDTO().getId()));
            } else {
                Long employeeId = employeeAssignDTO.getResourceManagerDTO().getEmployee().getId();
                checkIfRoleAlreadyExist(ResourceManager.class,employeeId );
                resourceManager = new ResourceManager();
                resourceManager.setEmployee(tryCast(Supervisor.class, employeeService.find(employeeId)));
            }
            project.setResourceManager(resourceManager);
        }
        if (!employeeAssignDTO.getSolutionArchitectDTOS().isEmpty()) {
            Set<SolutionArchitectDTO> solutionArchitectDTOS = employeeAssignDTO.getSolutionArchitectDTOS();
            Set<SolutionArchitect> solutionArchitects = new HashSet<>();
            solutionArchitectDTOS.forEach(solutionArchitectDTO -> {
                if (solutionArchitectDTO.getId() != null) {
                    solutionArchitects.add(tryCast(SolutionArchitect.class, projectRoleService.find(solutionArchitectDTO.getId())));
                } else {
                    Long employeeId = solutionArchitectDTO.getEmployee().getId();
                    checkIfRoleAlreadyExist(SolutionArchitect.class,employeeId );
                    SolutionArchitect solutionArchitect = new SolutionArchitect();
                    solutionArchitect.setEmployee(tryCast(Employee.class, employeeService.find(employeeId)));
                    solutionArchitects.add(solutionArchitect);
                }
            });
            project.getSolutionArchitect().addAll(solutionArchitects);
        }
        if (employeeAssignDTO.getBusinessLeaderDTO() != null) {
            BusinessLeader businessLeader;
            if (employeeAssignDTO.getBusinessLeaderDTO().getId() != null) {
                businessLeader = tryCast(BusinessLeader.class, projectRoleService.find(employeeAssignDTO.getBusinessLeaderDTO().getId()));
            } else {
                Long employeeId = employeeAssignDTO.getBusinessLeaderDTO().getEmployee().getId();
                checkIfRoleAlreadyExist(BusinessLeader.class,employeeId );
                businessLeader = new BusinessLeader();
                businessLeader.setEmployee(tryCast(BusinessEmployee.class, employeeService.find(employeeId)));
            }
            project.setBusinessLeader(businessLeader);
        }

        return projectMapper.convertToDto(update(project));
    }

    private Function<ProjectClassDTO, ProjectClass> mapProjectClassDTOToProjectClass() {
        return projectClassDTO -> Enum.valueOf(ProjectClass.class, projectClassDTO.name());
    }

    private Function<ProjectStatusDTO, ProjectStatus> mapProjectStatusDTOToProjectStatus() {
        return projectStatusDTO -> Enum.valueOf(ProjectStatus.class, projectStatusDTO.name());
    }

    private  <T extends ProjectRole> void  checkIfRoleAlreadyExist(Class<T> clazz, Long employeeId){
        List<ProjectRole> roles = projectRoleService.findAllRoleOfEmployee(employeeId);
        roles.forEach( role -> {
            if (clazz.isInstance(role)) {
                throw new ProjectRoleAlreadyExist("Employee with id:" + employeeId + "already have role:" + clazz.getName());
            }
        });
    }

    private <T, R> R tryCast(Class<T> t, Object r) {
        if (t.isInstance(r)) {
            return (R) r;
        } else {
            throw new WrongEmployeeTypeException(r + " is not employee type:" + t);
        }
    }

    @Override
    public IProjectDao getDao() {
        return projectDao;
    }
}

