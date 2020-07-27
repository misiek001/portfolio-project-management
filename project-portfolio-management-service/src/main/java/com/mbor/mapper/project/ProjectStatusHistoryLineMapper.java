package com.mbor.mapper.project;

import com.mbor.domain.ProjectStatusHistoryLine;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.ProjectStatusHistoryLineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectStatusHistoryLineMapper extends ToEntityMapper<ProjectStatusHistoryLineDTO, ProjectStatusHistoryLine> {

    public ProjectStatusHistoryLineMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProjectStatusHistoryLineDTO convertEntityToDto(ProjectStatusHistoryLine projectStatusHistoryLine) {
        return modelMapper.map(projectStatusHistoryLine, ProjectStatusHistoryLineDTO.class);
    }


    @Override
    public ProjectStatusHistoryLine convertDtoToEntity(ProjectStatusHistoryLineDTO projectStatusHistoryLineDTO) {
        return modelMapper.map(projectStatusHistoryLineDTO, ProjectStatusHistoryLine.class);
    }
}
