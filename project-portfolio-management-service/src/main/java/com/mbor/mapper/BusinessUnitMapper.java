package com.mbor.mapper;

import com.mbor.domain.BusinessUnit;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitMapper extends CreationPojoMapper<BusinessUnitDTO, BusinessUnit, BusinessUnitCreationDTO, BusinessUnitCreatedDTO> {

    public BusinessUnitMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BusinessUnit convertCreationDtoToEntity(BusinessUnitCreationDTO businessUnitCreationDTO) {
        return modelMapper.map(businessUnitCreationDTO, BusinessUnit.class);
    }

    @Override
    public BusinessUnitCreatedDTO convertEntityToCreatedDto(BusinessUnit businessUnit) {
        return modelMapper.map(businessUnit, BusinessUnitCreatedDTO.class);
    }

    @Override
    public BusinessUnitDTO convertToDto(BusinessUnit businessUnit) {
        return modelMapper.map(businessUnit, BusinessUnitDTO.class);
    }

    @Override
    public BusinessUnit convertToEntity(BusinessUnitDTO businessUnitDTO) {
        return modelMapper.map(businessUnitDTO, BusinessUnit.class);
    }
}
