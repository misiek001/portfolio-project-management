package com.mbor.mapper;

import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.model.ProjectStatusHistoryLineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectStatusHistoryLineMapper extends SimplePojoMapper<ProjectStatusHistoryLineDTO, ProjectStatusHistoryLine> {

    public ProjectStatusHistoryLineMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectStatusHistoryLineDTO convertToDto(ProjectStatusHistoryLine projectStatusHistoryLine) {
        return modelMapper.map(projectStatusHistoryLine, ProjectStatusHistoryLineDTO.class);
    }

    @Override
    public ProjectStatusHistoryLine convertToEntity(ProjectStatusHistoryLineDTO projectStatusHistoryLineDTO) {
        return modelMapper.map(projectStatusHistoryLineDTO, ProjectStatusHistoryLine.class);
    }
}
