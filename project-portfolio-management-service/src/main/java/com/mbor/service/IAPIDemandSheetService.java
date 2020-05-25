package com.mbor.service;

import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;

import java.util.List;

public interface IAPIDemandSheetService extends IAPIService<DemandSheetCreatedDTO, DemandSheetCreationDTO, DemandSheetDTO> {
    List<DemandSheetDTO> getAllDemandSheetsOfBRMWithNoProject(Long brmId);
}
