package com.mbor.mapper.employee;

import com.mbor.domain.employeeinproject.BusinessLeader;
import com.mbor.mapper.ToDtoMapper;
import com.mbor.model.BusinessLeaderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessLeaderMapper extends ToDtoMapper<BusinessLeaderDTO, BusinessLeader> {

    public BusinessLeaderMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BusinessLeaderDTO convertEntityToDto(BusinessLeader businessLeader) {
        BusinessLeaderDTO businessLeaderDTO =  modelMapper.map(businessLeader, BusinessLeaderDTO.class);
        businessLeaderDTO.setId(businessLeader.getId());
        return businessLeaderDTO;
    }

}
