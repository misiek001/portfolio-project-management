package com.mbor.mapper;

import com.mbor.domain.Consultant;
import com.mbor.model.ConsultantDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsultantMapper extends EmployeeMapper<ConsultantDTO, Consultant, EmployeeCreationDTO, EmployeeCreatedDTO> {

    public ConsultantMapper(ModelMapper modelMapper) {
        super(modelMapper, ConsultantDTO.class, Consultant.class, EmployeeCreationDTO.class, EmployeeCreatedDTO.class);
    }

}
