package com.misiek.mapping;

import com.misiek.domain.BusinessEmployee;
import com.misiek.model.BusinessEmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessEmployeeMapper extends Mapper<BusinessEmployeeDTO, BusinessEmployee> {

    public BusinessEmployeeMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BusinessEmployeeDTO convertToDto(BusinessEmployee businessEmployee) {
        return null;
    }

    @Override
    public BusinessEmployee convertToEntity(BusinessEmployeeDTO businessEmployeeDTO) {
        return modelMapper.map(businessEmployeeDTO, BusinessEmployee.class);
    }
}
