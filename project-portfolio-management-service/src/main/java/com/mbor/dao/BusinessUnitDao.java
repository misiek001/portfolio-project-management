package com.mbor.dao;

import com.mbor.domain.BusinessUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessUnitDao extends RawDao<BusinessUnit> implements IBusinessUnitDao {

    public BusinessUnitDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.clazz = BusinessUnit.class;
    }

    @Override
    public void update(BusinessUnit businessUnit) {
        try(Session session = sessionFactory.openSession()) {
            session.merge(businessUnit);
        }
    }
}
