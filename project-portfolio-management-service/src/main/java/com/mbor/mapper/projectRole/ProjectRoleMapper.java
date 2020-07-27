package com.mbor.mapper.projectRole;

import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.mapper.CreationPojoMapper;
import com.mbor.model.ProjectRoleDTO;
import com.mbor.model.creation.ProjectRoleCreatedDTO;
import com.mbor.model.creation.ProjectRoleCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectRoleMapper extends CreationPojoMapper<ProjectRoleDTO, ProjectRole, ProjectRoleCreationDTO, ProjectRoleCreatedDTO> {

    public ProjectRoleMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    //Todo
    public ProjectRole convertCreationDtoToEntity(ProjectRoleCreationDTO projectRoleCreationDTO) {
        return null;
    }

    @Override
    //Todo
    public ProjectRoleCreatedDTO convertEntityToCreatedDto(ProjectRole projectRole) {
        return null;
    }

    @Override
    public ProjectRoleDTO convertEntityToDto(ProjectRole projectRole) {
        return modelMapper.map(projectRole, ProjectRoleDTO.class);
    }

}
