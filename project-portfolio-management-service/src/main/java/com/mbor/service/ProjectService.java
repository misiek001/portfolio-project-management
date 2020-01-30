package com.mbor.service;

import com.mbor.dao.IProjectDao;
import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.mapper.ProjectMapper;
import com.mbor.model.ProjectClassDTO;
import com.mbor.model.ProjectDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.model.search.SearchProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService extends RawService<Project>  implements IProjectService<Project> {

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
        BusinessLeader businessLeader;
        if(projectCreationDTO.getBusinessLeader().getId() == null){
            businessLeader = new BusinessLeader();
            BusinessEmployee businessEmployee = (BusinessEmployee) employeeService.find(projectCreationDTO.getBusinessLeader().getEmployee().getId());
            businessLeader.setEmployee(businessEmployee);
        } else {
            businessLeader = (BusinessLeader) projectRoleService.find(projectCreationDTO.getBusinessLeader().getId());
        }
        project.setBusinessLeader(businessLeader);
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.find(projectCreationDTO.getBusinessRelationManager().getId()));

        projectCreationDTO.getBusinessUnits().forEach(businessUnit ->
                project.addBusinessUnit((BusinessUnit) businessUnitService.find(businessUnit.getId())));
        Project savedProject =  super.saveInternal(project);
        return projectMapper.convertEntityToCreatedDto(savedProject);
    }

    @Override
    public List<ProjectDTO> findByMultipleCriteria(SearchProjectDTO searchProjectDTO) {
        String projectName = searchProjectDTO.getProjectName();
        List<ProjectClass> projectClass = null;
        if(searchProjectDTO.getProjectClassDTOList() != null){
            projectClass = searchProjectDTO.getProjectClassDTOList()
                    .stream()
                    .map(mapProjectClassDTOToProjectClass())
                    .collect(Collectors.toList());
        }
        String businessUnitName = searchProjectDTO.getBusinessUnitName();
        List<ProjectStatus> projectStatusList = null;
        if(searchProjectDTO.getProjectStatusDTOList() != null){
            projectStatusList = searchProjectDTO.getProjectStatusDTOList()
                    .stream()
                    .map(mapProjectStatusDTOToProjectStatus())
                    .collect(Collectors.toList());
        }
        LocalDate projectStartDate = searchProjectDTO.getProjectStartDateLaterThat();
        System.out.println(businessUnitService.find(1l));
        List<Project>  foundProject = getDao().findByMultipleCriteria(projectName, projectClass, businessUnitName, projectStatusList, projectStartDate);
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        foundProject.forEach(project -> projectDTOList.add(projectMapper.convertToDto(project)));
        return projectDTOList;
    }

    private Function<ProjectClassDTO, ProjectClass> mapProjectClassDTOToProjectClass(){
        return projectClassDTO -> Enum.valueOf(ProjectClass.class, projectClassDTO.name());
    }

    private Function<ProjectStatusDTO, ProjectStatus> mapProjectStatusDTOToProjectStatus(){
        return projectStatusDTO  -> Enum.valueOf(ProjectStatus.class, projectStatusDTO.name());
    }

    @Override
    public IProjectDao getDao() {
        return projectDao;
    }
}

