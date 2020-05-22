package com.mbor.service;

import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;

public interface IDemandSheetService  extends IInternalDemandSheetService, IAPIService<DemandSheetCreatedDTO, DemandSheetCreationDTO, DemandSheetDTO> {
}
