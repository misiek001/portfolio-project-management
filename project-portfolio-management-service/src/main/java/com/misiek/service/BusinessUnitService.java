package com.misiek.service;

import com.misiek.dao.IDao;
import com.misiek.dao.BusinessUnitDao;
import com.misiek.domain.BusinessUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessUnitService extends RawService<BusinessUnit> {

    private BusinessUnitDao businessUnitDao;

    @Autowired
    public BusinessUnitService(BusinessUnitDao businessUnitDao) {
        this.businessUnitDao = businessUnitDao;
    }

    @Override
    public IDao getDao() {
        return businessUnitDao;
    }
}
