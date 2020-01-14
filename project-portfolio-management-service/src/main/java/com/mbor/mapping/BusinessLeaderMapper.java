package com.mbor.mapping;

import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.model.BusinessEmployeeDTO;
import com.mbor.model.BusinessLeaderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessLeaderMapper extends SimplePojoMapper<BusinessLeaderDTO, BusinessLeader> {

    private final BusinessEmployeeMapper businessEmployeeMapper;

    @Autowired
    public BusinessLeaderMapper(ModelMapper modelMapper, BusinessEmployeeMapper businessEmployeeMapper) {
        super(modelMapper);
        this.businessEmployeeMapper = businessEmployeeMapper;
    }

    @Override
    public BusinessLeaderDTO convertToDto(BusinessLeader businessLeader) {
        BusinessLeaderDTO businessLeaderDTO =  modelMapper.map(businessLeader, BusinessLeaderDTO.class);
        businessLeaderDTO.setId(businessLeader.getId());
        return businessLeaderDTO;
    }

    @Override
    public BusinessLeader convertToEntity(BusinessLeaderDTO businessLeaderDTO) {
        BusinessLeader businessLeader = modelMapper.map(businessLeaderDTO, BusinessLeader.class);
        businessLeader.setEmployee(businessEmployeeMapper.convertToEntity((BusinessEmployeeDTO) businessLeaderDTO.getEmployee()));
        return businessLeader;
    }


}
