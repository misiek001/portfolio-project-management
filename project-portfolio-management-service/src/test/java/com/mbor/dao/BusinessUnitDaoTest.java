package com.mbor.dao;

import com.mbor.configuration.TestConfiguration;
import com.mbor.domain.BusinessUnit;
import com.mbor.entityFactory.TestObjectFactory;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.AfterAll;
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

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ServiceConfiguration.class, TestConfiguration.class})
@ActiveProfiles("test")
@Transactional
class BusinessUnitDaoTest extends IDaoImplTest<BusinessUnit> {

    @Autowired
    IBusinessUnitDao businessUnitDao;

    @BeforeAll
    static void init(@Autowired EntityManagerFactory entityManagerFactory, @Autowired TestObjectFactory testObjectsFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction =  entityManager.getTransaction();
        transaction.begin();
        for (int i = 0; i < IDaoImplTest.CREATED_ENTITIES_NUMBER; i++) {
            BusinessUnit businessUnit = testObjectsFactory.prepareBusinessUnit();
            entityManager.persist(businessUnit);
            entityIdList.add(businessUnit.getId());
        }
        transaction.commit();
    }

    @AfterAll
    static void clear(@Autowired TableClearer tableClearer){
        tableClearer.clearTables();
        entityIdList.clear();
    }

    @Override
    protected IDao getDao() {
        return businessUnitDao;
    }

    @Override
    protected BusinessUnit createNewEntity() {
        return testObjectsFactory.prepareBusinessUnit();
    }
}