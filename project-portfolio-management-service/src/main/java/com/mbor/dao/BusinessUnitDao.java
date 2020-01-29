package com.mbor.dao;

import com.mbor.domain.BusinessUnit;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessUnitDao extends RawDao<BusinessUnit> implements IBusinessUnitDao {

    public BusinessUnitDao() {
        this.clazz = BusinessUnit.class;
    }
}


