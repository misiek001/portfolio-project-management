package com.mbor.mapper.employee;

import com.mbor.domain.BusinessEmployee;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessEmployeeMapper extends EmployeeMapper<BusinessEmployeeDTO, BusinessEmployee, EmployeeCreationDTO, EmployeeCreatedDTO>  {

    public BusinessEmployeeMapper(ModelMapper modelMapper) {
        super(modelMapper, BusinessEmployeeDTO.class, BusinessEmployee.class, EmployeeCreationDTO.class, EmployeeCreatedDTO.class);
    }

}
