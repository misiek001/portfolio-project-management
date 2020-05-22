package com.mbor.mapper;

import com.mbor.domain.DemandSheet;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import org.modelmapper.ModelMapper;
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
        return null;
    }

    @Override
    public DemandSheet convertToEntity(DemandSheetDTO demandSheetDTO) {
        return null;
    }
}
