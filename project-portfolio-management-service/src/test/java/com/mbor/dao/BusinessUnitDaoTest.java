package com.mbor.dao;

import com.mbor.domain.BusinessUnit;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles("test")
@Transactional
class BusinessUnitDaoTest extends IDaoImplTest<BusinessUnit> {

    @Autowired
    IBusinessUnitDao businessUnitDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            BusinessUnit businessUnit = new BusinessUnit();
            businessUnit.setName("BusinessUnit" + random.nextLong());
            entityManager.persist(businessUnit);
        }
        transaction.commit();
    }

    @Override
    protected IDao getDao() {
        return businessUnitDao;
    }

    @Override
    protected BusinessUnit createNewEntity() {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("BusinessUnit" + random.nextLong());
        return businessUnit;
    }
}