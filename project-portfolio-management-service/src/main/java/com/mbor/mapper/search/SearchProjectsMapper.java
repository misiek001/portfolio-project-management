package com.mbor.mapper.search;

import com.mbor.domain.search.SearchProject;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.search.SearchProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SearchProjectsMapper extends ToEntityMapper<SearchProjectDTO, SearchProject> {

    public SearchProjectsMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public SearchProjectDTO convertEntityToDto(SearchProject searchProject) {
        return modelMapper.map(searchProject, SearchProjectDTO.class);
    }

    @Override
    public SearchProject convertDtoToEntity(SearchProjectDTO searchProjectDTO) {
        return modelMapper.map(searchProjectDTO, SearchProject.class);
    }
}
