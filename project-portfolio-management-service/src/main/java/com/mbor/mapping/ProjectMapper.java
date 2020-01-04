package com.misiek.mapping;

import com.misiek.domain.BusinessUnit;
import com.misiek.domain.Project;
import com.misiek.domain.ProjectStatus;
import com.misiek.model.BusinessUnitDTO;
import com.misiek.model.ProjectDTO;
import com.misiek.model.creation.ProjectCreatedDTO;
import com.misiek.model.creation.ProjectCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper extends Mapper<ProjectDTO, Project> {

    private final BusinessLeaderMapper businessLeaderMapper;
    private final BusinessRelationManagerMapper businessRelationManagerMapper;

    public ProjectMapper(ModelMapper modelMapper, BusinessLeaderMapper businessLeaderMapper, BusinessRelationManagerMapper businessRelationManagerMapper) {
        super(modelMapper);
        this.businessLeaderMapper = businessLeaderMapper;
        this.businessRelationManagerMapper = businessRelationManagerMapper;
    }

    @Override
    public ProjectDTO convertToDto(Project project) {
        return null;
    }

    @Override
    public Project convertToEntity(ProjectDTO projectDTO) {
        return null;
    }

    public Project mapProjectCreationDTOtoProject(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        project.setProjectName(projectCreationDTO.getProjectName());
        project.setStatus(ProjectStatus.valueOf(projectCreationDTO.getProjectStatus().name()));
        project.setBusinessRelationManager(businessRelationManagerMapper.convertToEntity(projectCreationDTO.getBusinessRelationManager()));
        project.setBusinessLeader(businessLeaderMapper.convertToEntity(projectCreationDTO.getBusinessLeader()));
        project.getBusinessLeader().getEmployee().addProjectRole(project.getBusinessLeader());
        Set<BusinessUnit> businessUnits = projectCreationDTO.getBusinessUnits().stream()
                .map(businessUnitDTO -> {
                    BusinessUnit businessUnit = new BusinessUnit();
                    businessUnit.setId(businessUnitDTO.getId());
                    businessUnit.setName(businessUnitDTO.getName());
                return businessUnit;
        }).collect(Collectors.toSet());
        project.setBusinessUnits(businessUnits);
        return project;
    }

    public ProjectCreatedDTO mapCreatedProjectToDTO(Project project) {
        ProjectCreatedDTO projectCreatedDTO = modelMapper.map(project, ProjectCreatedDTO.class);
        projectCreatedDTO.setBusinessLeader(businessLeaderMapper.convertToDto(project.getBusinessLeader()));
        Set<BusinessUnitDTO> businessUnitDTOSet = project.getBusinessUnits().stream()
                .map(businessUnit -> {
                    BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
                    businessUnitDTO.setId(businessUnit.getId());
                    businessUnitDTO.setName(businessUnit.getName());
                    return businessUnitDTO;
                }).collect(Collectors.toSet());
        projectCreatedDTO.setBusinessUnits(businessUnitDTOSet);
        return projectCreatedDTO;
    }

}
