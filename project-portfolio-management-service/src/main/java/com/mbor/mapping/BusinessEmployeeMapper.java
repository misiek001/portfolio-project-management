package com.mbor.mapping;

import com.mbor.domain.BusinessEmployee;
import com.mbor.model.BusinessEmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessEmployeeMapper extends Mapper<BusinessEmployeeDTO, BusinessEmployee> {

    @Autowired
    public BusinessEmployeeMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BusinessEmployeeDTO convertToDto(BusinessEmployee businessEmployee) {
       return  modelMapper.map(businessEmployee, BusinessEmployeeDTO.class);
    }

    @Override
    public BusinessEmployee convertToEntity(BusinessEmployeeDTO businessEmployeeDTO) {
        return modelMapper.map(businessEmployeeDTO, BusinessEmployee.class);
    }
}
