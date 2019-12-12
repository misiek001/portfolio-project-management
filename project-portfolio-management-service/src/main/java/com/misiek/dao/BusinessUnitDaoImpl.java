package com.misiek.dao;

import com.misiek.domain.BusinessUnit;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessUnitDaoImpl extends RawDao<BusinessUnit> {

    public BusinessUnitDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = BusinessUnit.class;
    }
}
