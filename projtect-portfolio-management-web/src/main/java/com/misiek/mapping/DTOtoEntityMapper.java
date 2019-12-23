package com.misiek.mapping;

import com.misiek.domain.BusinessEmployee;
import com.misiek.domain.BusinessRelationManager;
import com.misiek.domain.Project;
import com.misiek.domain.employeeinproject.BusinessLeader;
import com.misiek.model.BusinessEmployeeDTO;
import com.misiek.model.BusinessLeaderDTO;
import com.misiek.model.BusinessRelationManagerDTO;
import com.misiek.model.ProjectDTO;
import com.misiek.model.creation.ProjectCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOtoEntityMapper extends Mapper {

    public DTOtoEntityMapper(ModelMapper modelMapper) {
        super(modelMapper);
        init();
    }

    private void init(){

}

    public Project mapProjectCreationDTOtoProject(ProjectCreationDTO projectCreationDTO){
        Project result = modelMapper.map(projectCreationDTO, Project.class);
        result.setBusinessRelationManager(mapBusinessRelationManagerDTOtoEntity(projectCreationDTO.getBusinessRelationManager()));
        result.setBusinessLeader(mamBusinessLeaderDTOToEntity(projectCreationDTO.getBusinessLeader()));
        return result;
    }

    public Project mapProjectDTOtoEntity(ProjectDTO projectDTO){
        return modelMapper.map(projectDTO, Project.class);
    }

    public BusinessRelationManager mapBusinessRelationManagerDTOtoEntity(BusinessRelationManagerDTO businessRelationManagerDTO){
        return modelMapper.map(businessRelationManagerDTO, BusinessRelationManager.class);
    }

    public BusinessLeader mamBusinessLeaderDTOToEntity(BusinessLeaderDTO businessLeaderDTO){
        BusinessLeader businessLeader = modelMapper.map(businessLeaderDTO, BusinessLeader.class);
        businessLeader.setEmployee(mapBusinessEmployeeDTOtoEntity((BusinessEmployeeDTO) businessLeaderDTO.getEmployee()));
        return businessLeader;
    }

    public BusinessEmployee mapBusinessEmployeeDTOtoEntity(BusinessEmployeeDTO businessEmployeeDTO){
        return modelMapper.map(businessEmployeeDTO, BusinessEmployee.class);
    }

}
