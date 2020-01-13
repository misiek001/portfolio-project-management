package com.mbor.mapping;

import com.mbor.domain.BusinessEmployee;
import com.mbor.domain.Employee;
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

    public Employee mapEmployeeCreationDTOtoEmployee(EmployeeCreationDTO employeeCreationDTO){
       return modelMapper.map(employeeCreationDTO, BusinessEmployee.class);
    }
}
