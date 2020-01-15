package com.mbor.mapper;

import com.mbor.domain.Supervisor;
import com.mbor.model.SupervisorDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupervisorMapper extends EmployeeMapper<SupervisorDTO, Supervisor, EmployeeCreationDTO, EmployeeCreatedDTO> {

    public SupervisorMapper(ModelMapper modelMapper) {
        super(modelMapper, SupervisorDTO.class, Supervisor.class, EmployeeCreationDTO.class, EmployeeCreatedDTO.class);
    }
}
