package com.mbor.mapper.project;

import com.mbor.domain.projectaspect.*;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.projectaspect.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectAspectLineMapper extends ToEntityMapper<ProjectAspectLineDTO, ProjectAspectLine> {

    public ProjectAspectLineMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectAspectLineDTO convertEntityToDto(ProjectAspectLine projectAspectLine) {
        return modelMapper.map(projectAspectLine, ProjectAspectLineDTO.class);
    }

    @Override
    public ProjectAspectLine convertDtoToEntity(ProjectAspectLineDTO projectAspectLineDTO) {
        return modelMapper.map(projectAspectLineDTO, ProjectAspectLine.class);
    }
}
