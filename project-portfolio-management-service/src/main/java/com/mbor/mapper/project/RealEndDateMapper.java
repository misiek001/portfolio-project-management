package com.mbor.mapper.project;

import com.mbor.domain.RealEndDate;
import com.mbor.mapper.ToEntityMapper;
import com.mbor.model.RealEndDateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RealEndDateMapper extends ToEntityMapper<RealEndDateDTO, RealEndDate> {

    public RealEndDateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public RealEndDateDTO convertEntityToDto(RealEndDate realEndDate) {
        return modelMapper.map(realEndDate, RealEndDateDTO.class);
    }

    @Override
    public RealEndDate convertDtoToEntity(RealEndDateDTO realEndDateDTO) {
        return modelMapper.map(realEndDateDTO, RealEndDate.class);
    }
}
