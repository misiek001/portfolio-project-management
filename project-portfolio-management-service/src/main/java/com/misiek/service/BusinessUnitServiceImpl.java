package com.misiek.service;

import com.misiek.dao.AbstractDao;
import com.misiek.dao.BusinessUnitDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessUnitServiceImpl extends RawService {

    private BusinessUnitDaoImpl businessUnitDao;

    @Autowired
    public BusinessUnitServiceImpl(BusinessUnitDaoImpl businessUnitDao) {
        this.businessUnitDao = businessUnitDao;
    }

    @Override
    public AbstractDao getDao() {
        return businessUnitDao;
    }
}
