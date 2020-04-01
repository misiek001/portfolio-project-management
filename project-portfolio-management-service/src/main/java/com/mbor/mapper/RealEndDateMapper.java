package com.mbor.mapper;

import com.mbor.domain.RealEndDate;
import com.mbor.model.RealEndDateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RealEndDateMapper extends SimplePojoMapper <RealEndDateDTO, RealEndDate> {

    public RealEndDateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public RealEndDateDTO convertToDto(RealEndDate realEndDate) {
        return modelMapper.map(realEndDate, RealEndDateDTO.class);
    }

    @Override
    public RealEndDate convertToEntity(RealEndDateDTO realEndDateDTO) {
        return modelMapper.map(realEndDateDTO, RealEndDate.class);
    }

}
