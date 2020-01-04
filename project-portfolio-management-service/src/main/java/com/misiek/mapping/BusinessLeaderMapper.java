package com.misiek.mapping;

import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.model.BusinessEmployeeDTO;
import com.misiek.model.BusinessLeaderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessLeaderMapper extends Mapper<BusinessLeaderDTO, BusinessLeader> {

    private final BusinessEmployeeMapper businessEmployeeMapper;

    @Autowired
    public BusinessLeaderMapper(ModelMapper modelMapper, BusinessEmployeeMapper businessEmployeeMapper) {
        super(modelMapper);
        this.businessEmployeeMapper = businessEmployeeMapper;
    }

    @Override
    public BusinessLeaderDTO convertToDto(BusinessLeader businessLeader) {
        return modelMapper.map(businessLeader, BusinessLeaderDTO.class);
    }

    @Override
    public BusinessLeader convertToEntity(BusinessLeaderDTO businessLeaderDTO) {
        BusinessLeader businessLeader = modelMapper.map(businessLeaderDTO, BusinessLeader.class);
        businessLeader.setEmployee(businessEmployeeMapper.convertToEntity((BusinessEmployeeDTO) businessLeaderDTO.getEmployee()));
        return businessLeader;
    }


}
