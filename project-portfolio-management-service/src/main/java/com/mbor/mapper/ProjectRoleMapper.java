package com.mbor.mapper;

import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.model.ProjectRoleDTO;
import com.mbor.model.creation.ProjectRoleCreatedDTO;
import com.mbor.model.creation.ProjectRoleCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectRoleMapper extends  CreationPojoMapper<ProjectRoleDTO, ProjectRole, ProjectRoleCreationDTO, ProjectRoleCreatedDTO> {

    public ProjectRoleMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectRole convertCreationDtoToEntity(ProjectRoleCreationDTO projectRoleCreationDTO) {
        return null;
    }

    @Override
    public ProjectRoleCreatedDTO convertEntityToCreatedDto(ProjectRole projectRole) {
        return null;
    }

    @Override
    public ProjectRoleDTO convertToDto(ProjectRole projectRole) {
        return modelMapper.map(projectRole, ProjectRoleDTO.class);
    }

    @Override
    public ProjectRole convertToEntity(ProjectRoleDTO projectRoleDTO) {
        return null;
    }
}
