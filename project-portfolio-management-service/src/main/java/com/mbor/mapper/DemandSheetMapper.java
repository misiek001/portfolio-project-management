package com.mbor.mapper;

import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.domain.Project;
import com.mbor.model.BusinessRelationManagerDTO;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.ProjectDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class DemandSheetMapper  extends CreationPojoMapper<DemandSheetDTO, DemandSheet, DemandSheetCreationDTO, DemandSheetCreatedDTO> {

    public DemandSheetMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public DemandSheet convertCreationDtoToEntity(DemandSheetCreationDTO demandSheetCreationDTO) {
        DemandSheet demandSheet = new DemandSheet();
        demandSheet.setProjectName(demandSheetCreationDTO.getProjectName());
        demandSheet.setDescription(demandSheetCreationDTO.getDescription());
        return demandSheet;
    }

    @Override
    public DemandSheetCreatedDTO convertEntityToCreatedDto(DemandSheet demandSheet) {
        return modelMapper.map(demandSheet, DemandSheetCreatedDTO.class);
    }

    @Override
    public DemandSheetDTO convertToDto(DemandSheet demandSheet) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setImplicitMappingEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(DemandSheet.class, DemandSheetDTO.class)
                .addMappings(mapping -> mapping.map(DemandSheet::getBusinessUnit, DemandSheetDTO::setBusinessUnit))
                .addMappings(mapping -> mapping.map(DemandSheet::getBusinessRelationManager, DemandSheetDTO::setBusinessRelationManager))
                .addMappings(mapping -> mapping.map(DemandSheet::getProject, DemandSheetDTO::setProject))
                .addMappings(mapping -> mapping.map(DemandSheet::getId, DemandSheetDTO::setId))
                .addMappings(mapping -> mapping.map(DemandSheet::getProjectName, DemandSheetDTO::setProjectName));
        modelMapper.typeMap(BusinessUnit.class, BusinessUnitDTO.class)
                .addMappings(mapping -> mapping.map(BusinessUnit::getId, BusinessUnitDTO::setId));
        modelMapper.typeMap(BusinessRelationManager.class, BusinessRelationManagerDTO.class)
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setProjects))
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setDirector))
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setAssignedBusinessUnits))
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setBusinessUnit))
                .addMappings(mapping -> mapping.skip(BusinessRelationManagerDTO::setProjectRoleSet))
                .implicitMappings();
        modelMapper.typeMap(Project.class, ProjectDTO.class)
                .addMappings(mapping -> mapping.map(Project::getId,ProjectDTO::setId))
                .addMappings(mapping -> mapping.map(Project::getProjectName,ProjectDTO::setProjectName));
       return modelMapper.map(demandSheet, DemandSheetDTO.class);
    }

    @Override
    public DemandSheet convertToEntity(DemandSheetDTO demandSheetDTO) {
        return null;
    }
}
