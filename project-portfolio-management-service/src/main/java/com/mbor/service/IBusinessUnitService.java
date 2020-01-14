package com.mbor.service;

import com.mbor.domain.BusinessUnit;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;

public interface IBusinessUnitService<T extends BusinessUnit> extends IService<T> {

    BusinessUnitCreatedDTO save(BusinessUnitCreationDTO businessUnitCreationDTO);

}
