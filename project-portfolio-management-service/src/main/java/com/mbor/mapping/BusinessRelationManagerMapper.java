package com.mbor.mapping;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessRelationManagerMapper extends EmployeeMapper <BusinessRelationManagerDTO, BusinessRelationManager, EmployeeCreationDTO, EmployeeCreatedDTO> {

    public BusinessRelationManagerMapper(ModelMapper modelMapper) {
        super(modelMapper, BusinessRelationManagerDTO.class, BusinessRelationManager.class, EmployeeCreationDTO.class, EmployeeCreatedDTO.class);
    }
}
