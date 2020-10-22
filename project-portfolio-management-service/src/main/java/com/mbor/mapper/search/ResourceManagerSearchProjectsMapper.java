package com.mbor.mapper.search;

import com.mbor.domain.search.ResourceManagerSearchProject;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.search.ResourceManagerSearchProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ResourceManagerSearchProjectsMapper extends ToEntityMapper<ResourceManagerSearchProjectDTO, ResourceManagerSearchProject> {

    public ResourceManagerSearchProjectsMapper(@Qualifier(value = "resourceManagerSearchProjectModelMapper")ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ResourceManagerSearchProjectDTO convertEntityToDto(ResourceManagerSearchProject resourceManagerSearchProject) {
        return modelMapper.map(resourceManagerSearchProject, ResourceManagerSearchProjectDTO.class);
    }

    @Override
    public ResourceManagerSearchProject convertDtoToEntity(ResourceManagerSearchProjectDTO resourceManagerSearchProjectDTO) {
        return modelMapper.map(resourceManagerSearchProjectDTO, ResourceManagerSearchProject.class);
    }
}
