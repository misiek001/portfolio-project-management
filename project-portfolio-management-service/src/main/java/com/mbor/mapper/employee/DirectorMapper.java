package com.mbor.mapper.employee;

import com.mbor.domain.Director;
import com.mbor.model.DirectorDTO;
import com.mbor.model.creation.EmployeeCreatedDTO;
import com.mbor.model.creation.EmployeeCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DirectorMapper extends EmployeeMapper<DirectorDTO, Director, EmployeeCreationDTO, EmployeeCreatedDTO> {

    public DirectorMapper(ModelMapper modelMapper) {
        super(modelMapper, DirectorDTO.class, Director.class, EmployeeCreationDTO.class, EmployeeCreatedDTO.class);
    }
}
