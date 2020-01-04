package com.mbor.service;

import com.mbor.dao.BusinessUnitDao;
import com.mbor.dao.IBusinessUnitDao;
import com.mbor.dao.IDao;
import com.mbor.domain.BusinessUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class BusinessUnitService extends RawService<BusinessUnit> implements IBusinessUnitService<BusinessUnit> {

    private final IBusinessUnitDao businessUnitDao;

    @Autowired
    public BusinessUnitService(BusinessUnitDao businessUnitDao) {
        this.businessUnitDao = businessUnitDao;
    }

    @Override
    public IDao getDao() {
        return businessUnitDao;
    }
}
