package com.mbor.service;

import com.mbor.dao.IBusinessUnitDao;
import com.mbor.dao.IDao;
import com.mbor.domain.BusinessUnit;
import com.mbor.mapping.BusinessUnitMapper;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class BusinessUnitService extends RawService<BusinessUnit> implements IBusinessUnitService<BusinessUnit> {

    private final IBusinessUnitDao businessUnitDao;

    private final BusinessUnitMapper businessUnitMapper;

    @Autowired
    public BusinessUnitService(IBusinessUnitDao businessUnitDao, BusinessUnitMapper businessUnitMapper) {
        this.businessUnitDao = businessUnitDao;
        this.businessUnitMapper = businessUnitMapper;
    }

    @Override
    public BusinessUnitCreatedDTO save(BusinessUnitCreationDTO businessUnitCreationDTO) {
        BusinessUnit businessUnit = businessUnitMapper.convertCreationDtoToEntity(businessUnitCreationDTO);
        businessUnit = super.saveInternal(businessUnit);
        return businessUnitMapper.convertEntityToCreatedDto(businessUnit);
    }

    @Override
    public IDao getDao() {
        return businessUnitDao;
    }
}
