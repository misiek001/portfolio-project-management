package com.mbor.mapper.project;

import com.mbor.domain.ProjectRequest;
import com.mbor.mapper.CreationPojoMapper;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProjectRequestMapper extends CreationPojoMapper<ProjectRequestDTO, ProjectRequest, ProjectRequestCreationDTO, com.mbor.model.creation.ProjectRequestDTO> {

    public ProjectRequestMapper(@Qualifier("ProjectRequestModelMapper") ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectRequest convertCreationDtoToEntity(ProjectRequestCreationDTO projectRequestCreationDTO) {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName(projectRequestCreationDTO.getProjectName());
        projectRequest.setDescription(projectRequestCreationDTO.getDescription());
        return projectRequest;
    }

    @Override
    public com.mbor.model.creation.ProjectRequestDTO convertEntityToCreatedDto(ProjectRequest projectRequest) {
        return modelMapper.map(projectRequest, com.mbor.model.creation.ProjectRequestDTO.class);
    }

    @Override
    public ProjectRequestDTO convertEntityToDto(ProjectRequest projectRequest) {
       return modelMapper.map(projectRequest, ProjectRequestDTO.class);
    }

}
