package com.mbor.service;

import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;

public interface IBusinessUnitService extends IInternalBusinessUnitService, IAPIService<BusinessUnitCreatedDTO, BusinessUnitCreationDTO, BusinessUnitDTO>  {

}
