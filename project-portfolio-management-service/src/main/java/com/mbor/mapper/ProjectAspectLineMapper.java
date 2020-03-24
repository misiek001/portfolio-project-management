package com.mbor.mapper;

import com.mbor.domain.projectaspect.*;
import com.mbor.model.projectaspect.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectAspectLineMapper extends SimplePojoMapper<ProjectAspectLineDTO, ProjectAspectLine> {

    public ProjectAspectLineMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectAspectLineDTO convertToDto(ProjectAspectLine projectAspectLine) {
        return modelMapper.map(projectAspectLine, ProjectAspectLineDTO.class);
    }

    @Override
    public ProjectAspectLine convertToEntity(ProjectAspectLineDTO projectAspectLineDTO) {
        return modelMapper.map(projectAspectLineDTO, ProjectAspectLine.class);
    }
}
