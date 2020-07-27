package com.mbor.mapper.project;

import com.mbor.domain.DemandSheet;
import com.mbor.mapper.CreationPojoMapper;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DemandSheetMapper  extends CreationPojoMapper<DemandSheetDTO, DemandSheet, DemandSheetCreationDTO, DemandSheetCreatedDTO> {

    public DemandSheetMapper(@Qualifier("demandSheetModelMapper") ModelMapper modelMapper) {
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
    public DemandSheetDTO convertEntityToDto(DemandSheet demandSheet) {
       return modelMapper.map(demandSheet, DemandSheetDTO.class);
    }

}
