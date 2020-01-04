package com.mbor.dao;

import com.mbor.domain.BusinessUnit;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessUnitDao extends RawDao<BusinessUnit> implements IBusinessUnitDao {

    public BusinessUnitDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = BusinessUnit.class;
    }
}
