package com.mbor.mapper;

import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.Project;
import com.mbor.domain.ProjectStatus;
import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.ProjectDTO;
import com.mbor.model.ProjectStatusDTO;
import com.mbor.model.creation.ProjectCreatedDTO;
import com.mbor.model.creation.ProjectCreationDTO;
import com.mbor.service.IBusinessUnitService;
import com.mbor.service.IInternalEmployeeService;
import com.mbor.service.IProjectRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper extends CreationPojoMapper<ProjectDTO, Project, ProjectCreationDTO, ProjectCreatedDTO> {

    private final BusinessLeaderMapper businessLeaderMapper;
    private final BusinessRelationManagerMapper businessRelationManagerMapper;
    private final ProjectAspectLineMapper projectAspectMapper;

    private final IInternalEmployeeService employeeService;
    private final IBusinessUnitService businessUnitService;
    private final IProjectRoleService projectRoleService;


    public ProjectMapper(ModelMapper modelMapper, BusinessLeaderMapper businessLeaderMapper, BusinessRelationManagerMapper businessRelationManagerMapper, ProjectAspectLineMapper projectAspectMapper, IInternalEmployeeService employeeService, IBusinessUnitService businessUnitService, IProjectRoleService projectRoleService) {
        super(modelMapper);
        this.businessLeaderMapper = businessLeaderMapper;
        this.businessRelationManagerMapper = businessRelationManagerMapper;
        this.projectAspectMapper = projectAspectMapper;
        this.employeeService = employeeService;
        this.businessUnitService = businessUnitService;
        this.projectRoleService = projectRoleService;
    }

    @Override
    public ProjectDTO convertToDto(Project project) {
        return  modelMapper.map(project, ProjectDTO.class);
    }

    @Override
    public Project convertToEntity(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    @Override
    public ProjectCreatedDTO convertEntityToCreatedDto(Project project) {
        ProjectCreatedDTO projectCreatedDTO = modelMapper.map(project, ProjectCreatedDTO.class);
        projectCreatedDTO.setProjectStatus(Enum.valueOf(ProjectStatusDTO.class, project.getProjectStatus().name()));
        projectCreatedDTO.setBusinessLeader(businessLeaderMapper.convertToDto(project.getBusinessLeader()));
        Set<BusinessUnitDTO> businessUnitDTOSet = project.getBusinessUnits().stream()
                .map(businessUnit -> {
                    BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
                    businessUnitDTO.setId(businessUnit.getId());
                    return businessUnitDTO;
                }).collect(Collectors.toSet());
        projectCreatedDTO.setBusinessUnits(businessUnitDTOSet);
        return projectCreatedDTO;
    }

    @Override
    public Project convertCreationDtoToEntity(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        project.setProjectName(projectCreationDTO.getProjectName());
        project.setProjectStatus(ProjectStatus.valueOf(projectCreationDTO.getProjectStatus().name()));
        project.setBusinessRelationManager((BusinessRelationManager) employeeService.findInternal(projectCreationDTO.getBusinessRelationManager().getId()));
        BusinessLeader businessLeader;
        if(projectCreationDTO.getBusinessLeader().getId() == null){
            businessLeader = new BusinessLeader();
            BusinessEmployee businessEmployee = (BusinessEmployee) employeeService.findInternal(projectCreationDTO.getBusinessLeader().getEmployee().getId());
            businessLeader.setEmployee(businessEmployee);
        } else {
            businessLeader = (BusinessLeader) projectRoleService.findInternal(projectCreationDTO.getBusinessLeader().getId());
        }
        project.setBusinessLeader(businessLeader);


        projectCreationDTO.getBusinessUnits().forEach(businessUnit ->
                project.addBusinessUnit((businessUnitService.findInternal(businessUnit.getId()))));
        return project;
    }

}
