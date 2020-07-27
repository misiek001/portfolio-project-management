package com.mbor.mapper.businessUnit;

import com.mbor.domain.BusinessUnit;
import com.mbor.mapper.CreationPojoMapper;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitMapper extends CreationPojoMapper<BusinessUnitDTO, BusinessUnit, BusinessUnitCreationDTO, BusinessUnitCreatedDTO> {

    public BusinessUnitMapper(@Qualifier("businessUnitModelMapper") ModelMapper modelMapper) {
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
    public BusinessUnitDTO convertEntityToDto(BusinessUnit businessUnit) {
        return modelMapper.map(businessUnit, BusinessUnitDTO.class);
    }

}
