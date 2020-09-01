package com.mbor.mapper.search;

import com.mbor.domain.search.SupervisorSearchProject;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.search.SupervisorSearchProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupervisorSearchProjectMapper extends ToEntityMapper<SupervisorSearchProjectDTO, SupervisorSearchProject> {

    public SupervisorSearchProjectMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public SupervisorSearchProjectDTO convertEntityToDto(SupervisorSearchProject supervisorSearchProject) {
        return modelMapper.map(supervisorSearchProject, SupervisorSearchProjectDTO.class);
    }

    @Override
    public SupervisorSearchProject convertDtoToEntity(SupervisorSearchProjectDTO supervisorSearchProjectDTO) {
        return modelMapper.map(supervisorSearchProjectDTO, SupervisorSearchProject.class);
    }
}
