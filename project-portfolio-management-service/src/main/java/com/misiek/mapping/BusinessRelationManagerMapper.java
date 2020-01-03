package com.misiek.mapping;

import com.misiek.domain.BusinessRelationManager;
import com.misiek.model.BusinessRelationManagerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessRelationManagerMapper extends Mapper<BusinessRelationManagerDTO, BusinessRelationManager> {

    public BusinessRelationManagerMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BusinessRelationManagerDTO convertToDto(BusinessRelationManager businessRelationManager) {
        return null;
    }

    @Override
    public BusinessRelationManager convertToEntity(BusinessRelationManagerDTO businessRelationManagerDTO) {
        return modelMapper.map(businessRelationManagerDTO, BusinessRelationManager.class);
    }
}
